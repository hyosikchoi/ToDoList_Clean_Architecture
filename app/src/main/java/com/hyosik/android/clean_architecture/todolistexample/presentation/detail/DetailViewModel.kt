package com.hyosik.android.clean_architecture.todolistexample.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyosik.android.clean_architecture.todolistexample.BaseViewModel
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.domain.DeleteToDoItemUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.GetToDoItemUseCase
import com.hyosik.android.clean_architecture.todolistexample.domain.InsertToDoItemUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
   var detailMode : DetailMode,
   var id : Long = -1 ,
   private val getToDoItemUseCase : GetToDoItemUseCase,
   private val insertToDoItemUseCase: InsertToDoItemUseCase,
   private val deleteToDoItemUseCase : DeleteToDoItemUseCase
) : BaseViewModel() {

    var _toDoDetailLiveData  =  MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val toDoDetailLiveData  : LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job  = viewModelScope.launch {

        when(detailMode) {

            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)

                try {
                    getToDoItemUseCase(id)?.let { toDoEntity ->
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoItem = toDoEntity))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }

            }

            DetailMode.WRITE -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }

        }

    }

    fun insertToDo(title : String , description : String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)

        when(detailMode) {

            DetailMode.DETAIL -> {
                // TODO update 관련은 추후 구현
            }

            DetailMode.WRITE -> {
                try {
                    val toDoEntity = ToDoEntity(
                        title = title,
                        description = description
                    )
                   id = insertToDoItemUseCase(toDoEntity = toDoEntity)
                   _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                   detailMode = DetailMode.DETAIL
                }

                catch (e:Exception) {
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }

        }


    }

    fun deleteToDo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            deleteToDoItemUseCase(id)
            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
        }
        catch (e : Exception) {
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }


}