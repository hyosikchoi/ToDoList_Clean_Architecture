package com.hyosik.android.clean_architecture.todolistexample.presentation.detail

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity

sealed class ToDoDetailState {

    object UnInitialized : ToDoDetailState()

    object Loading : ToDoDetailState()

    data class Success(
        val toDoItem: ToDoEntity
    ) : ToDoDetailState()

    object Write : ToDoDetailState()

    object Modify : ToDoDetailState()

    object Delete : ToDoDetailState()

    object Error : ToDoDetailState()
}
