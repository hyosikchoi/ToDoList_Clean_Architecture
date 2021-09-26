package com.hyosik.android.clean_architecture.todolistexample

import androidx.lifecycle.ViewModel
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    abstract fun fetchData() : Job

}