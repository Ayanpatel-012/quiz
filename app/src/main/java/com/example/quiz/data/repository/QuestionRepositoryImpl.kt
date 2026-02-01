package com.example.quiz.data.repository

import com.example.quiz.data.local.dao.QuestionDao
import com.example.quiz.data.mapper.toEntity
import com.example.quiz.data.mapper.toResponse
import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.data.remote.QuestionService
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val questionService: QuestionService,
    private val questionDao: QuestionDao
) : QuestionRepository {
    
    override suspend fun fetchAndStoreQuestions(limit: Int): List<QuestionResponse> {
        val questions = questionService.getQuestions(limit)
        
        questionDao.deleteAllQuestions()
        
        val entities = questions.map { it.toEntity() }
        questionDao.insertQuestions(entities)
        
        return questions
    }
    
    override suspend fun getStoredQuestions(): List<QuestionResponse> {
        return questionDao.getAllQuestions().map { it.toResponse() }
    }
    
    override suspend fun clearQuestions() {
        questionDao.deleteAllQuestions()
    }
}
