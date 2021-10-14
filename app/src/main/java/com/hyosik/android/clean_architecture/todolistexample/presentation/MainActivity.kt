package com.hyosik.android.clean_architecture.todolistexample.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyosik.android.clean_architecture.todolistexample.BaseActivity
import com.hyosik.android.clean_architecture.todolistexample.R
import com.hyosik.android.clean_architecture.todolistexample.data.entity.ToDoEntity
import com.hyosik.android.clean_architecture.todolistexample.databinding.ActivityMainBinding
import com.hyosik.android.clean_architecture.todolistexample.extension.toast
import com.hyosik.android.clean_architecture.todolistexample.presentation.detail.DetailActivity
import com.hyosik.android.clean_architecture.todolistexample.presentation.detail.DetailMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class MainActivity : BaseActivity<MainListViewModel>() , CoroutineScope {

    private lateinit var binding : ActivityMainBinding

    private var todoAdapter = ToDoAdapter()

    override val viewModel: MainListViewModel by viewModel()

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")

    private val resultLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.fetchData()
            }
        }

    override fun observeToDoList() {
        viewModel.toDoListLiveData.observe(this) {

            when(it) {
               is ToDoListState.UnInitialized -> {
                    initViews(binding)
                }

               is ToDoListState.Loading -> {
                    handleLoading()
                }

               is ToDoListState.Success -> {
                    handleSuccess(it.toDoList)
                }

               is ToDoListState.Delete -> {
                   handleDeleteAll(it.toDoEmptyList)
               }

               is ToDoListState.Error -> {
                   handleError()
               }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun initViews(binding: ActivityMainBinding) = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity , LinearLayoutManager.VERTICAL , false)
        recyclerView.adapter = todoAdapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        // DetailActivity 로 이동
        addToDoButton.setOnClickListener {
            resultLauncher.launch(DetailActivity.getIntent(this@MainActivity , DetailMode.WRITE))
        }

        // 전체 삭제 버튼
        deleteAllButton.setOnClickListener {
            viewModel.deleteAll()
        }

    }

    private fun handleLoading() {
        binding.refreshLayout.isRefreshing = true
    }

    private fun handleSuccess(toDoList : List<ToDoEntity>) {
        binding.refreshLayout.isRefreshing = false
        binding.refreshLayout.isEnabled = toDoList.isNotEmpty()

        if(toDoList.isEmpty()) {
            binding.emptyResultTextView.isGone = false
            binding.recyclerView.isGone = true
        }
        else {
            binding.emptyResultTextView.isGone = true
            binding.recyclerView.isGone = false
            todoAdapter.submitList(toDoList)
            todoAdapter.setToDoListener(
                toDoItemClickListener = {
                    resultLauncher.launch(DetailActivity.getIntent(this , DetailMode.DETAIL , it.id))
                },
                toDoCheckListener = {
                    //toast("hasCompleted : ${it.hasCompleted}")
                    viewModel.updateToDo(toDoEntity = it)
                }
            )
        }

    }

    private fun handleDeleteAll(toDoList: List<ToDoEntity>) = with(binding) {
        refreshLayout.isRefreshing = false
        refreshLayout.isEnabled = toDoList.isNotEmpty()

        if(toDoList.isEmpty()) {
            binding.emptyResultTextView.isGone = false
            binding.recyclerView.isGone = true
            toast("삭제되었습니다.")
        }

        else {
            toast("삭제에 실패했습니다.")
        }

    }

    private fun handleError() {
        toast("에러가 발생했습니다.")
    }

}