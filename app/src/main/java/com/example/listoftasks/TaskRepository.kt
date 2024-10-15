package com.example.listoftasks

class TaskRepository {
    private var _tasksList = mutableListOf<Task>()
    private var id = 1

    fun getList() : List<Task>{
        return _tasksList.toList()
    }
    fun plus(text: String){
        _tasksList.add(Task(id++, text))
    }

    fun delete(task: Task){
        _tasksList.remove(task)
    }

    fun update(task: Task) {
        val index = _tasksList.indexOfFirst { it.id == task.id }

        if (index != -1) {
            _tasksList[index] = task
        }
    }

}
