package com.example.quiz.data.mapper

import com.example.quiz.data.local.entity.QuestionEntity
import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.data.model.QuestionText

fun QuestionResponse.toEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        category = category,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers,
        questionText = question.text,
        tags = tags,
        type = type,
        difficulty = difficulty,
        regions = regions,
        isNiche = isNiche
    )
}

fun QuestionEntity.toResponse(): QuestionResponse {
    return QuestionResponse(
        id = id,
        category = category,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers,
        question = QuestionText(text = questionText),
        tags = tags,
        type = type,
        difficulty = difficulty,
        regions = regions,
        isNiche = isNiche
    )
}
