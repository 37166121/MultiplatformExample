package top.aliyunm.example.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.aliyunm.example.common.lifecycleOwner
import top.aliyunm.example.common.viewModelStoreOwner
import top.aliyunm.example.ui.home.HomePage
import top.aliyunm.example.ui.login.LoginPage

/**
 * 路由
 */
object Router {

    /**
     * 首页
     */
    val HOME = "Home"

    /**
     * 登录页面
     */
    val LOGIN = "Login"

    @Composable
    fun init() {
        navController = rememberNavController()
        navController?.setLifecycleOwner(lifecycleOwner)
        navController?.setViewModelStore(viewModelStoreOwner.viewModelStore)

        CompositionLocalProvider(LocalNavController provides navController!!) {
            // 配置可以访问的页面
            NavHost(navController = navController!!, startDestination = HOME) {
                composable(HOME) { HomePage.BaseUI() }
                composable(LOGIN) { LoginPage.BaseUI() }
            }
        }
    }
}

// 全局导航
val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}

var navController: NavHostController? = null
