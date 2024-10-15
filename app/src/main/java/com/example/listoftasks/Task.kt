package com.example.listoftasks

data class Task(
    var id: Int,
    var title: String,
    var isEditing: Boolean = false
)

