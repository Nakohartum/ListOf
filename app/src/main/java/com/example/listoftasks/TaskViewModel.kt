package com.example.listoftasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel() : ViewModel() {
    private val repository: TaskRepository = TaskRepository()

    private val _counter = MutableStateFlow<List<Task>>(emptyList())

    var text = mutableStateOf("")
    init {
        _counter.value = repository.getList()
    }
    val counter = _counter.asStateFlow()

    fun plus(){
        repository.plus(text.value)
        _counter.value = repository.getList()
    }

    fun delete(task: Task){
        repository.delete(task)
        _counter.value = repository.getList()
    }

    fun update(task: Task){
        repository.update(task)
        _counter.value = repository.getList()
    }
}
