package com.hyosik.android.clean_architecture.todolistexample.domain

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository

class UpdateToDoItemUseCase(
    val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(toDoEntity: ToDoEntity) {
        return toDoRepository.updateToDoItem(toDoEntity = toDoEntity)
    }

}