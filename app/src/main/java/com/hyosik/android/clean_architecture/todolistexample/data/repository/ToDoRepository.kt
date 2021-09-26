package com.hyosik.android.clean_architecture.todolistexample.data.repository

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList() : List<ToDoEntity>

    suspend fun insertToDoItem(toDoEntity: ToDoEntity) : Long

}