package com.hyosik.android.clean_architecture.todolistexample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyosik.android.clean_architecture.todolistexample.BaseViewModel
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.domain.GetToDoListUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.InsertToDoItemUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainListViewModel(
    val getToDoListUseCase: GetToDoListUseCase
) : BaseViewModel() {

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData : LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job  = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }




}