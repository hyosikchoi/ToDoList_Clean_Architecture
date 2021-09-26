package com.hyosik.android.clean_architecture.todolistexample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity

@Database(entities = [ToDoEntity::class] , version = 1 , exportSchema = false)
abstract class ToDoDatabase  : RoomDatabase() {

    abstract fun toDoDao() : ToDoDao

}