package top.aliyunm.example.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import top.aliyunm.example.Ext.next
import top.aliyunm.example.base.BasePage
import top.aliyunm.example.router.Router.LOGIN
import top.aliyunm.example.router.navController
import top.aliyunm.example.viewmodel.HomeViewModel

object HomePage : BasePage<HomeViewModel>() {
    override val title: String
        get() = "首页"

    @Composable
    override fun setViewModel() {
        viewModel = viewModel { HomeViewModel() }
    }

    override fun initData() {
        isShowBack = false
        isShowBottomBar = true
    }

    @Composable
    override fun initView() {
        HomeUI()
    }

    @Composable
    fun HomeUI() {
        Text("Home", modifier = Modifier.clickable {
            navController.next(LOGIN)
        })
    }
}