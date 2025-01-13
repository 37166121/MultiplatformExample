package top.aliyunm.example.ui.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import top.aliyunm.example.base.BasePage
import top.aliyunm.example.viewmodel.HomeViewModel

object HomePage : BasePage<HomeViewModel>() {
    override val title: String
        get() = "首页"

    @Composable
    override fun setViewModel() {
        viewModel = viewModel { HomeViewModel() }
    }

    override fun initData() {

    }

    @Composable
    override fun initView() {
        HomeUI()
    }

    @Composable
    fun HomeUI() {

    }
}