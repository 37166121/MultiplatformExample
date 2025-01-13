package top.aliyunm.example.ui.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import top.aliyunm.example.base.BasePage
import top.aliyunm.example.viewmodel.LoginViewModel

object LoginPage : BasePage<LoginViewModel>() {
    override val title: String
        get() = "登录"

    @Composable
    override fun setViewModel() {
        viewModel = viewModel { LoginViewModel() }
    }

    override fun initData() {

    }

    @Composable
    override fun initView() {
        LoginUI()
    }

    @Composable
    fun LoginUI() {

    }
}