package com.hyosik.android.clean_architecture.todolistexample.domain

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.data.repository.DefaultToDoRepository
import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository

class InsertToDoItemUseCase(
    val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(toDoEntity: ToDoEntity) : Long {
        return toDoRepository.insertToDoItem(toDoEntity = toDoEntity)
    }

}