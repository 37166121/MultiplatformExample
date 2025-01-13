package top.aliyunm.example

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import top.aliyunm.example.listener.NetworkListener
import top.aliyunm.example.platform.getPlatform
import top.aliyunm.example.router.Router
import top.aliyunm.example.utils.DataStoreUtils
import top.aliyunm.example.viewmodel.CommonViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

var commonViewModel: CommonViewModel? = null

fun getCommonVM(): CommonViewModel {
    return commonViewModel!!
}

@Composable
@Preview
fun App() {
    commonViewModel = viewModel { CommonViewModel() }
    // 注册SDK和功能
    registered()
}

/**
 * 初始化功能、注册SDK
 */
@Composable
fun registered() {
    println("Platform ${getPlatform().name}")

    // 初始化本地数据存储
    DataStoreUtils.init()

    // 初始化网络监听器
    NetworkListener.init()
    NetworkListener.listeners.add {
        println("网络状态：$it")
    }

    // 初始化路由
    Router.init()
}

inline fun<reified VM: ViewModel> getViewModel(): VM {
    return viewModelFactory { }.create(VM::class, MutableCreationExtras())
}