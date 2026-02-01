package com.example.quiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.quiz.data.local.converter.StringListConverter

@Entity(tableName = "questions")
@TypeConverters(StringListConverter::class)
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val category: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val questionText: String,
    val tags: List<String>,
    val type: String,
    val difficulty: String,
    val regions: List<String>,
    val isNiche: Boolean
)
