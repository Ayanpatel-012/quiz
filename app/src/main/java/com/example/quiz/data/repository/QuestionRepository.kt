package com.example.quiz.data.repository

import com.example.quiz.data.model.QuestionResponse

interface QuestionRepository {
    suspend fun fetchAndStoreQuestions(limit: Int): List<QuestionResponse>
    suspend fun getStoredQuestions(): List<QuestionResponse>
    suspend fun clearQuestions()
}
