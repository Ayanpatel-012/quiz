package com.example.quiz.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.quiz.ui.screens.QuizScreen
import com.example.quiz.ui.screens.StartQuizScreen
import com.example.quiz.ui.state.QuizUiState
import com.example.quiz.ui.theme.QuizTheme
import com.example.quiz.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val uiState by viewModel.uiState.collectAsState()
                    
                    when (uiState) {
                        is QuizUiState.Initial -> {
                            StartQuizScreen(
                                onStartQuiz = {
                                    viewModel.startQuiz()
                                }
                            )
                        }
                        else -> {
                            QuizScreen(
                                uiState = uiState,
                                onAnswerSelected = { answer ->
                                    viewModel.selectAnswer(answer)
                                },
                                onSubmitAnswer = {
                                    viewModel.submitAnswer()
                                },
                                onNextQuestion = {
                                    viewModel.moveToNextQuestion()
                                },
                                onRestartQuiz = {
                                    viewModel.restartQuiz()
                                },
                                onStartNewQuiz = {
                                    viewModel.startNewQuiz()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    
    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }
}
