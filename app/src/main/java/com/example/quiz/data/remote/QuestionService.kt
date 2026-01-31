package com.example.quiz.data.remote

import com.example.quiz.data.model.QuestionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionService {
    @GET("v2/questions")
    suspend fun getQuestions(
        @Query("limit") limit: Int
    ): List<QuestionResponse>
}
