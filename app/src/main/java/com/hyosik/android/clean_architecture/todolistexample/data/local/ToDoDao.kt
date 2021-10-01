package com.hyosik.android.clean_architecture.todolistexample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Query("Select * from ToDoEntity")
    suspend fun getAll() : List<ToDoEntity>

    @Query("Select * from ToDoEntity where id = :id")
    suspend fun getToDoItem(id : Long) : ToDoEntity

    @Insert
    suspend fun insertToDoItem(toDoEntity: ToDoEntity) : Long

}