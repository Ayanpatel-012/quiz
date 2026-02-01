package com.example.quiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiz.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)
    
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuestionEntity>
    
    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()
    
    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionsCount(): Int
}
