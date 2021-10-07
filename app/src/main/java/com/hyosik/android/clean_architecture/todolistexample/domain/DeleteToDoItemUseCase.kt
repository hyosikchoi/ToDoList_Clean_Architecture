package com.hyosik.android.clean_architecture.todolistexample.domain

import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository

class DeleteToDoItemUseCase(
    val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke(itemId : Long) {
        return toDoRepository.deleteToDoItem(itemId = itemId)
    }

}