package com.example.quiz.domain.usecase

import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.data.repository.QuestionRepository
import javax.inject.Inject

class QuestionUseCaseImpl @Inject constructor(
    private val questionRepository: QuestionRepository
) : QuestionUseCase {
    
    override suspend fun fetchQuestions(limit: Int): List<QuestionResponse> {
        return questionRepository.fetchAndStoreQuestions(limit)
    }
    
    override suspend fun getStoredQuestions(): List<QuestionResponse> {
        return questionRepository.getStoredQuestions()
    }
}
