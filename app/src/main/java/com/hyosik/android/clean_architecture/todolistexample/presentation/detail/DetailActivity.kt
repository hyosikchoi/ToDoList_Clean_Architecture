package com.hyosik.android.clean_architecture.todolistexample.presentation.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import com.hyosik.android.clean_architecture.todolistexample.BaseActivity
import com.hyosik.android.clean_architecture.todolistexample.databinding.ActivityDetailBinding
import com.hyosik.android.clean_architecture.todolistexample.extension.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity  : BaseActivity<DetailViewModel>(){

    private lateinit var binding: ActivityDetailBinding

    override val viewModel: DetailViewModel by viewModel {
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongExtra(TODO_ID_KEY , -1)
        )
    }

    override fun observeToDoList() {
        viewModel.toDoDetailLiveData.observe(this) { toDoDetailState ->
            when(toDoDetailState) {

                is ToDoDetailState.UnInitialized -> {
                    initViews()
                }

                is ToDoDetailState.Loading -> {
                    handleLoading()
                }

                is ToDoDetailState.Success -> {
                    handleSuccess(toDoDetailState)
                }

                is ToDoDetailState.Write -> {
                    handleWrite()
                }

                is ToDoDetailState.Modify -> {
                    handleModify()
                }

                is ToDoDetailState.Delete -> {
                    toast("성공적으로 삭제되었습니다.")
                    finish()
                }

                is ToDoDetailState.Error -> {
                    toast("예기치 못한 에러가 발생했습니다.")
                    finish()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setResult(Activity.RESULT_OK)

    }

    private fun initViews() = with(binding) {
        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = true

        deleteButton.setOnClickListener {
            viewModel.deleteToDo()
        }
        modifyButton.setOnClickListener {
            viewModel.setModifyToDoDetail()
        }
        updateButton.setOnClickListener {
            viewModel.insertToDo(title = titleInput.text.toString() , description = descriptionInput.text.toString())
        }

    }

    private fun handleLoading() = with(binding) {
        progrressBar.isGone = false
    }

    private fun handleWrite() = with(binding) {

        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        updateButton.isGone = false

    }

    private fun handleModify() = with(binding) {
        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        deleteButton.isGone = true
        modifyButton.isGone = true

        updateButton.isGone = false

    }

    private fun handleSuccess(toDoDetailState: ToDoDetailState.Success) = with(binding) {
        progrressBar.isGone = true

        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = false
        modifyButton.isGone = false
        updateButton.isGone = true

        titleInput.setText(toDoDetailState.toDoItem.title)
        descriptionInput.setText(toDoDetailState.toDoItem.description)

    }

    companion object {

        const val DETAIL_MODE_KEY = "DetailMode"
        const val TODO_ID_KEY = "ToDoId"

        /** floating action button 으로 새로 추가 진입 시 */
        fun getIntent(context : Context, detailMode : DetailMode) = Intent(context , DetailActivity::class.java).apply {
            putExtra(DETAIL_MODE_KEY , detailMode)
        }

        /** list 에서 클릭 해서 수정이나 삭제 하러 진입 시 */
        fun getIntent(context : Context , detailMode: DetailMode ,  id: Long) = Intent(context , DetailActivity::class.java).apply {
            putExtra(DETAIL_MODE_KEY , detailMode)
            putExtra(TODO_ID_KEY , id)
        }

    }

}