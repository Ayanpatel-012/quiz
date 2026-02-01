package com.example.quiz.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.model.QuestionResponse
import com.example.quiz.domain.usecase.QuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val questionUseCase: QuestionUseCase
) : ViewModel() {
  init {
      loadQuestions(10)
  }
    private val _questions = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questions: StateFlow<List<QuestionResponse>> = _questions.asStateFlow()


    fun loadQuestions(limit: Int) {
        viewModelScope.launch {

            try {
                val result = questionUseCase.fetchQuestions(limit)
                _questions.value = result
            } catch (e: Exception) {
                Log.d("TEST1234", "error")
            }
            val x = questionUseCase.getStoredQuestions()
            Log.d("TEST1234", "${x.size}")
        }


    }
}
