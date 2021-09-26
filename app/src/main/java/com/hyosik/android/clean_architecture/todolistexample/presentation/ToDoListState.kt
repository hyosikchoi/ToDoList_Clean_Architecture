package com.hyosik.android.clean_architecture.todolistexample.presentation

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import java.util.*
sealed class ToDoListState {

    object UnInitialized : ToDoListState()

    object Loading : ToDoListState()

    data class Success(
        val toDoList: List<ToDoEntity>
    ) : ToDoListState()

    object Error : ToDoListState()

}




