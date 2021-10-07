package com.hyosik.android.clean_architecture.todolistexample.data.repository

import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.data.local.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultToDoRepository(
    val toDoDao: ToDoDao,
    val ioDispatcher: CoroutineDispatcher
) : ToDoRepository {

    override suspend fun getToDoList(): List<ToDoEntity> = withContext(ioDispatcher) {
        toDoDao.getAll()
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity = withContext(ioDispatcher) {
         toDoDao.getToDoItem(itemId)
    }

    override suspend fun insertToDoItem(toDoEntity: ToDoEntity): Long  = withContext(ioDispatcher) {
        toDoDao.insertToDoItem(toDoEntity = toDoEntity)
    }

    override suspend fun deleteToDoItem(itemId: Long) {
        toDoDao.deleteToDoItem(itemId)
    }

}