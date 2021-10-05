package com.hyosik.android.clean_architecture.todolistexample.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import com.hyosik.android.clean_architecture.todolistexample.BaseActivity
import com.hyosik.android.clean_architecture.todolistexample.databinding.ActivityDetailBinding
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

                }

                is ToDoDetailState.Write -> {
                    handleWrite()
                }

                is ToDoDetailState.Modify -> {

                }

                is ToDoDetailState.Delete -> {

                }

                is ToDoDetailState.Error -> {
                    Toast.makeText(this , "예기치 못한 에러가 발생했습니다." , Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun initViews() = with(binding) {
        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = true

        deleteButton.setOnClickListener {
            //todo 추후 삭제 구현
        }
        modifyButton.setOnClickListener {
            //todo 추후 수정 구현
        }
        updateButton.setOnClickListener {
            //todo 추후 저장 구현
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