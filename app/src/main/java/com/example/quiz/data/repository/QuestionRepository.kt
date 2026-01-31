package com.example.quiz.data.repository

import com.example.quiz.data.model.QuestionResponse

interface QuestionRepository {
    suspend fun getQuestions(limit: Int): List<QuestionResponse>
}
