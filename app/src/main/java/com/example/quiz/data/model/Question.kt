package com.example.quiz.data.model

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("correctAnswer")
    val correctAnswer: String,
    @SerializedName("incorrectAnswers")
    val incorrectAnswers: List<String>,
    @SerializedName("question")
    val question: QuestionText,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("regions")
    val regions: List<String>,
    @SerializedName("isNiche")
    val isNiche: Boolean
)

data class QuestionText(
    @SerializedName("text")
    val text: String
)
