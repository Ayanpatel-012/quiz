package com.example.quiz.ui.state

import com.example.quiz.data.model.QuestionResponse

sealed class QuizUiState {
    object Initial : QuizUiState()
    object Loading : QuizUiState()
    data class Quiz(
        val currentQuestion: QuestionResponse,
        val currentQuestionIndex: Int,
        val totalQuestions: Int,
        val shuffledOptions: List<String>,
        val timeLeft: Int,
        val selectedAnswer: String?,
        val isAnswerLocked: Boolean,
        val isAnswerCorrect: Boolean?
    ) : QuizUiState()
    data class Error(val message: String) : QuizUiState()
    object Completed : QuizUiState()
}
