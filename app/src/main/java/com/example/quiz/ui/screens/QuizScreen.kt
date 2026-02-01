package com.example.quiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quiz.R
import com.example.quiz.ui.state.QuizUiState
import com.example.quiz.ui.theme.Dimens

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit = {}
) {
    when (uiState) {
        is QuizUiState.Initial -> {}
        
        is QuizUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        
        is QuizUiState.Quiz -> {
            QuizContent(
                state = uiState,
                onAnswerSelected = onAnswerSelected,
                onSubmitAnswer = onSubmitAnswer,
                onNextQuestion = onNextQuestion
            )
        }
        
        is QuizUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        is QuizUiState.Completed -> {
            CompletedScreen(
                score = uiState.score,
                totalQuestions = uiState.totalQuestions,
                onRestartQuiz = onRestartQuiz
            )
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizUiState.Quiz,
    onAnswerSelected: (String) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.PaddingStandard),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        id = R.string.question_counter,
                        state.currentQuestionIndex + 1,
                        state.totalQuestions
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                TimerDisplay(
                    timeLeft = state.timeLeft,
                    isLocked = state.isAnswerLocked
                )
            }
            
            Text(
                text = state.currentQuestion.question.text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.PaddingLarge)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.PaddingLarge),
                verticalArrangement = Arrangement.spacedBy(Dimens.PaddingSmall)
            ) {
                state.shuffledOptions.forEach { option ->
                    OptionItem(
                        option = option,
                        isSelected = state.selectedAnswer == option,
                        isCorrectAnswer = option == state.currentQuestion.correctAnswer,
                        isLocked = state.isAnswerLocked,
                        showFeedback = state.isAnswerLocked,
                        onClick = { onAnswerSelected(option) }
                    )
                }
            }
            
            if (state.isAnswerLocked && state.isAnswerCorrect != null) {
                FeedbackMessage(isCorrect = state.isAnswerCorrect)
            }
        }
        
        if (state.selectedAnswer != null && !state.isAnswerLocked) {
            Button(
                onClick = onSubmitAnswer,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.submit_answer),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Dimens.PaddingSmall)
                )
            }
        }
        
        if (state.isAnswerLocked) {
            Button(
                onClick = onNextQuestion,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.next_question),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Dimens.PaddingSmall)
                )
            }
        }
    }
}

@Composable
private fun TimerDisplay(
    timeLeft: Int,
    isLocked: Boolean
) {
    val backgroundColor = if (timeLeft <= 3) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.CornerRadiusSmall))
            .background(backgroundColor)
            .padding(horizontal = Dimens.PaddingSmall, vertical = 4.dp)
    ) {
        Text(
            text = if (isLocked) {
                stringResource(id = R.string.time_up)
            } else {
                stringResource(id = R.string.time_left, timeLeft)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun OptionItem(
    option: String,
    isSelected: Boolean,
    isCorrectAnswer: Boolean,
    isLocked: Boolean,
    showFeedback: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        showFeedback && isCorrectAnswer -> MaterialTheme.colorScheme.tertiary
        showFeedback && isSelected && !isCorrectAnswer -> MaterialTheme.colorScheme.error
        isSelected && !showFeedback -> com.example.quiz.ui.theme.DarkGray
        else -> MaterialTheme.colorScheme.surface
    }
    
    val textColor = when {
        showFeedback && isCorrectAnswer -> MaterialTheme.colorScheme.onTertiary
        showFeedback && isSelected && !isCorrectAnswer -> MaterialTheme.colorScheme.onError
        isSelected && !showFeedback -> com.example.quiz.ui.theme.White
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    val borderColor = when {
        showFeedback && isCorrectAnswer -> MaterialTheme.colorScheme.tertiary
        showFeedback && isSelected && !isCorrectAnswer -> MaterialTheme.colorScheme.error
        isSelected && !showFeedback -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
        else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.CornerRadiusMedium))
            .background(backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(Dimens.CornerRadiusMedium)
            )
            .clickable(
                enabled = !isLocked,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(Dimens.PaddingStandard)
    ) {
        Text(
            text = option,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun FeedbackMessage(isCorrect: Boolean) {
    val backgroundColor = if (isCorrect) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }
    
    val textColor = if (isCorrect) {
        MaterialTheme.colorScheme.onTertiary
    } else {
        MaterialTheme.colorScheme.onError
    }
    
    val message = if (isCorrect) {
        stringResource(id = R.string.correct_answer)
    } else {
        stringResource(id = R.string.incorrect_answer)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.PaddingStandard)
            .clip(RoundedCornerShape(Dimens.CornerRadiusMedium))
            .background(backgroundColor)
            .padding(Dimens.PaddingStandard),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CompletedScreen(
    score: Int,
    totalQuestions: Int,
    onRestartQuiz: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.PaddingStandard),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.quiz_completed),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = stringResource(id = R.string.your_score),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Dimens.PaddingLarge)
        )
        
        Text(
            text = stringResource(id = R.string.score_format, score, totalQuestions),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Dimens.PaddingSmall)
        )
        
        Button(
            onClick = onRestartQuiz,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.PaddingLarge),
            shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(id = R.string.restart_quiz),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = Dimens.PaddingSmall)
            )
        }
    }
}
