package com.example.quiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.domain.usecase.QuestionUseCase
import com.example.quiz.ui.state.QuizUiState
import com.example.quiz.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val questionUseCase: QuestionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Initial)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var questions: List<QuestionResponse> = emptyList()
    private var currentQuestionIndex = 0
    private var timerJob: Job? = null

    fun startQuiz() {
        viewModelScope.launch {
            _uiState.value = QuizUiState.Loading
            try {
                questions = questionUseCase.fetchQuestions(Constants.TOTAL_QUESTIONS)
                currentQuestionIndex = 0
                loadCurrentQuestion()
            } catch (e: Exception) {
                _uiState.value = QuizUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun loadCurrentQuestion() {
        if (currentQuestionIndex >= questions.size) {
            _uiState.value = QuizUiState.Completed
            return
        }

        val question = questions[currentQuestionIndex]
        val shuffledOptions = (question.incorrectAnswers + question.correctAnswer).shuffled()

        _uiState.value = QuizUiState.Quiz(
            currentQuestion = question,
            currentQuestionIndex = currentQuestionIndex,
            totalQuestions = questions.size,
            shuffledOptions = shuffledOptions,
            timeLeft = Constants.QUESTION_TIME_LIMIT_SECONDS,
            selectedAnswer = null,
            isAnswerLocked = false,
            isAnswerCorrect = null
        )

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            val currentState = _uiState.value as? QuizUiState.Quiz ?: return@launch
            var timeLeft = currentState.timeLeft

            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
                
                val state = _uiState.value as? QuizUiState.Quiz ?: return@launch
                _uiState.value = state.copy(timeLeft = timeLeft)
            }

            if (timeLeft == 0) {
                lockAnswer()
            }
        }
    }

    fun selectAnswer(answer: String) {
        val currentState = _uiState.value as? QuizUiState.Quiz ?: return
        if (currentState.isAnswerLocked) return

        _uiState.value = currentState.copy(selectedAnswer = answer)
    }

    fun submitAnswer() {
        val currentState = _uiState.value as? QuizUiState.Quiz ?: return
        if (currentState.isAnswerLocked || currentState.selectedAnswer == null) return

        val isCorrect = currentState.selectedAnswer == currentState.currentQuestion.correctAnswer
        
        timerJob?.cancel()
        _uiState.value = currentState.copy(
            isAnswerLocked = true,
            isAnswerCorrect = isCorrect
        )
    }

    private fun lockAnswer() {
        val currentState = _uiState.value as? QuizUiState.Quiz ?: return
        timerJob?.cancel()
        
        val isCorrect = if (currentState.selectedAnswer != null) {
            currentState.selectedAnswer == currentState.currentQuestion.correctAnswer
        } else {
            false
        }
        
        _uiState.value = currentState.copy(
            isAnswerLocked = true,
            isAnswerCorrect = isCorrect
        )
    }

    fun moveToNextQuestion() {
        currentQuestionIndex++
        loadCurrentQuestion()
    }
}
