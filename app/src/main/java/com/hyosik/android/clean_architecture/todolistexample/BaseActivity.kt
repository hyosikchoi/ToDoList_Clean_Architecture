package com.hyosik.android.clean_architecture.todolistexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseActivity<VM: BaseViewModel> : AppCompatActivity() {

    abstract val viewModel : VM

    private lateinit var fetchJob : Job

    abstract fun observeToDoList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchJob = viewModel.fetchData()

        observeToDoList()
    }

    override fun onDestroy() {
        if(fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()

    }

}