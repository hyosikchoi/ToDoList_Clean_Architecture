package com.hyosik.android.clean_architecture.todolistexample.di

import android.content.Context
import androidx.room.Room
import com.hyosik.android.clean_architecture.todolistexample.data.local.ToDoDao
import com.hyosik.android.clean_architecture.todolistexample.data.local.ToDoDatabase
import com.hyosik.android.clean_architecture.todolistexample.data.repository.DefaultToDoRepository
import com.hyosik.android.clean_architecture.todolistexample.data.repository.ToDoRepository
import com.hyosik.android.clean_architecture.todolistexample.domain.*
import com.hyosik.android.clean_architecture.todolistexample.presentation.MainListViewModel
import com.hyosik.android.clean_architecture.todolistexample.presentation.detail.DetailMode
import com.hyosik.android.clean_architecture.todolistexample.presentation.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


internal fun provideDB(context: Context) : ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java , "ToDoDB").build()

internal fun provideDao(database: ToDoDatabase) : ToDoDao = database.toDoDao()

internal val appModule = module {

    // Coroutine Dispatcher 정의
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // db , dao
    single { provideDB(androidApplication()) }
    single { provideDao(get()) }

    // repository
    single<ToDoRepository> { DefaultToDoRepository(get() , get()) }

    //UseCase
    factory { GetToDoListUseCase(get())}
    factory { InsertToDoItemUseCase(get())}
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { UpdateToDoItemUseCase(get()) }
    factory { DeleteToDoListUseCase(get()) }

    //ViewModel
    viewModel { MainListViewModel(get() , get() , get())}
    viewModel { (detailMode : DetailMode, id : Long) -> DetailViewModel(
        detailMode = detailMode,
        id = id,
        get(),
        get(),
        get(),
        get()
    )}
}