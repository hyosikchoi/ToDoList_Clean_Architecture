package com.hyosik.android.clean_architecture.todolistexample.domain

import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository

class DeleteToDoListUseCase(
    val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke() {
       toDoRepository.deleteAll()
    }

}