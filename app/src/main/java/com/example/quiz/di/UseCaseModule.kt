package com.example.quiz.di

import com.example.quiz.domain.usecase.QuestionUseCase
import com.example.quiz.domain.usecase.QuestionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindQuestionUseCase(
        questionUseCaseImpl: QuestionUseCaseImpl
    ): QuestionUseCase
}
