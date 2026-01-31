package com.example.quiz.domain.usecase

import com.example.quiz.data.model.QuestionResponse

interface QuestionUseCase {
    suspend fun fetchQuestions(limit: Int): List<QuestionResponse>
}
