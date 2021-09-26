package com.hyosik.android.clean_architecture.todolistexample.domain

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.data.repository.DefaultToDoRepository
import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository

class GetToDoListUseCase(
    val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke() : List<ToDoEntity> {
        return toDoRepository.getToDoList()
    }

}