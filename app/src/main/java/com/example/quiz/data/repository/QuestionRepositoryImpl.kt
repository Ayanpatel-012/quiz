package com.example.quiz.data.repository

import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.data.remote.QuestionService
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionService: QuestionService
) : QuestionRepository {
    
    override suspend fun getQuestions(limit: Int): List<QuestionResponse> {
        return questionService.getQuestions(limit)
    }
}
