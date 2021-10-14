package com.hyosik.android.clean_architecture.todolistexample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyosik.android.clean_architecture.todolistexample.BaseViewModel
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.domain.DeleteToDoListUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.GetToDoListUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.InsertToDoItemUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.UpdateToDoItemUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainListViewModel(
    val getToDoListUseCase: GetToDoListUseCase,
    val updateToDoItemUseCase: UpdateToDoItemUseCase,
    val deleteToDoListUseCase: DeleteToDoListUseCase
) : BaseViewModel() {

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData : LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job  = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }


    fun updateToDo(toDoEntity: ToDoEntity) = viewModelScope.launch {
        updateToDoItemUseCase(toDoEntity = toDoEntity)
    }

    fun deleteAll() = viewModelScope.launch {

        _toDoListLiveData.postValue(ToDoListState.Loading)

        try {
            deleteToDoListUseCase()
            _toDoListLiveData.postValue(ToDoListState.Delete(getToDoListUseCase()))
        }

        catch (e: Exception) {
            _toDoListLiveData.postValue(ToDoListState.Error)
        }


    }

}