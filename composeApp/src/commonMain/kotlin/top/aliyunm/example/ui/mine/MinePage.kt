package top.aliyunm.example.ui.mine

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import top.aliyunm.example.base.BasePage
import top.aliyunm.example.viewmodel.MineViewModel

object MinePage : BasePage<MineViewModel>() {
    override val title: String
        get() = "我的"

    @Composable
    override fun setViewModel() {
        viewModel = viewModel { MineViewModel() }
    }

    override fun initData() {
        isShowBack = false
        isShowBottomBar = true
    }

    @Composable
    override fun initView() {
        MineUI()
    }

    @Composable
    fun MineUI() {
        Text("Mine")
    }
}