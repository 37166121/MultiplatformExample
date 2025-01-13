package top.aliyunm.example.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import top.aliyunm.example.Ext.back
import top.aliyunm.example.Ext.backTo
import top.aliyunm.example.Ext.getRouter
import top.aliyunm.example.Ext.next
import top.aliyunm.example.Ext.nextAndClearThis
import top.aliyunm.example.platform.setImmersiveMode
import top.aliyunm.example.router.Router.HOME
import top.aliyunm.example.router.navController
import top.aliyunm.example.state.NetworkErrorHandler
import top.aliyunm.example.state.Toast
import example.composeapp.generated.resources.Res
import example.composeapp.generated.resources.compose_multiplatform
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

abstract class BasePage<VM : ViewModel> {

    lateinit var viewModel: VM

    /**
     * 页面标题
     */
    abstract val title: String

    /**
     * 是否显示头部标题栏
     */
    var isShowTopBar = true

    /**
     * 是否显示底部导航栏
     */
    var isShowBottomBar = false

    /**
     * 是否显示返回按钮
     */
    var isShowBack = true

    /**
     * 是否显示关闭按钮
     */
    var isShowClose = false

    /**
     * 是否需要占满宽度
     */
    open var isFillWidth = false

    /**
     * 是否添加页面间距
     */
    var isNeedPadding = true

    /**
     * 页面间距
     */
    // open var contentStart: Dp = if (isFillWidth) 0.dp else 10.dp

    // @OptIn(ExperimentalMaterial3Api::class)
    // var contentTop: Dp =
    //     if (isShowTopBar) TopAppBarDefaults.TopAppBarExpandedHeight + 10.dp else TopAppBarDefaults.MediumAppBarCollapsedHeight
    // open var contentEnd: Dp = if (isFillWidth) 0.dp else 10.dp
    // var contentBottom: Dp = 15.dp

    /**
     * 没有边距的Box
     */
    lateinit var bottomButton: @Composable BoxScope.() -> Unit

    lateinit var lazyList: LazyListScope.() -> Unit

    @Composable
    fun BaseUI() {
        setImmersiveMode()

        setViewModel()

        initData()

        var contentStart = if (isFillWidth) 0.dp else 10.dp
        var contentTop = 0.dp
        var contentEnd = if (isFillWidth) 0.dp else 10.dp
        var contentBottom = 0.dp

        MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF8B5CCC), background = Color(0XFFF2F2F2))) {
            val modifier = if (isNeedPadding) {
                Modifier.padding(
                    contentStart,
                    contentTop,
                    contentEnd,
                    contentBottom
                )
            } else Modifier.padding()

            Scaffold(
                topBar = {
                    if (isShowTopBar) {
                        setTopBar()
                    }
                },
                content = {
                    AnimatedVisibility(
                        true
                    ) {
                        NetworkErrorHandler()
                        Toast()
                        Box(
                            modifier = modifier.fillMaxSize()
                        ) {
                            initView()
                            if (this@BasePage::lazyList.isInitialized) {
                                LazyColumn(content = {
                                    lazyList()
                                    item {
                                        Spacer(modifier = Modifier.height(50.dp))
                                    }
                                })
                            }
                        }
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .padding(0.dp, 0.dp, 0.dp, contentBottom)
                        ) {
                            if (this@BasePage::bottomButton.isInitialized) {
                                bottomButton()
                            }
                        }
                    }
                },
                bottomBar = {
                    if (isShowBottomBar) {
                        setBottomBar()
                    }
                }
            )
        }
    }

    /**
     * 设置顶部标题栏
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun setTopBar() {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        title,
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                        textAlign = TextAlign.Start
                    )
                }
            },
            navigationIcon = {
                if (isShowBack) {
                    IconButton(onClick = {
                        back()
                    }, modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            back()
                        }, onLongPress = {
                            close()
                        })
                    }) {
                        Image(
                            painterResource(Res.drawable.compose_multiplatform),
                            alignment = Alignment.CenterStart,
                            contentDescription = "返回"
                        )
                    }
                }
            },
            actions = {
                if (isShowClose) {
                    IconButton(onClick = {
                        close()
                    }) {
                        Image(
                            painterResource(Res.drawable.compose_multiplatform),
                            alignment = Alignment.CenterEnd,
                            contentDescription = "关闭"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(TopAppBarDefaults.TopAppBarExpandedHeight)
        )
    }

    /**
     * 设置底部导航栏
     */
    @Composable
    fun setBottomBar() {
        BottomAppBar(Modifier.fillMaxWidth()) {

        }
    }

    /**
     * 返回
     */
    fun back() {
        CoroutineScope(Dispatchers.Main).launch {
            if (navController.getRouter() != HOME) {
                navController.back()
            }
        }
    }

    fun backTo(pageName: String) {
        if (navController.getRouter() != HOME) {
            navController.backTo(pageName)
        }
    }

    /**
     * 关闭
     */
    fun close() {
        backTo(HOME)
    }

    /**
     * 下一页
     * @param pageName 要前往的页面
     * @param isClose 是否关闭当前页面
     * @param singleTop 唯一的、在顶部的页面（在栈中唯一，且在栈顶）
     */
    fun next(
        pageName: String,
        isClose: Boolean = false,
        singleTop: Boolean = true,
        params: Map<String, String?> = mapOf()
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            params.forEach {
                getSavedState()?.set(it.key, it.value)
            }
            if (isClose) {
                navController.nextAndClearThis(navController.getRouter(), pageName)
            } else {
                navController.next(pageName, singleTop)
            }
        }
    }

    /**
     * 获取上一个页面的状态管理器
     */
    fun getPreviousSavedState(): SavedStateHandle? {
        return navController?.previousBackStackEntry?.savedStateHandle
    }

    /**
     * 获取本页面的状态管理器
     */
    fun getCurrentSavedState(): SavedStateHandle? {
        return navController?.currentBackStackEntry?.savedStateHandle
    }

    /**
     * 获取指定页面的状态管理器
     */
    fun getSavedState(router: String = ""): SavedStateHandle? {
        return if (router.isBlank()) {
            getCurrentSavedState()
        } else {
            navController?.getBackStackEntry(router)?.savedStateHandle
        }
    }

    fun <T> getStateFlow(key: String, initialValue: T, router: String = ""): StateFlow<T>? {
        return getSavedState(router)?.getStateFlow(key, initialValue)
    }

    fun <T> getParams(key: String, router: String = ""): T? {
        return getSavedState(router)?.get(key)
    }

    fun <T> getPreviousParams(key: String): T? {
        return getPreviousSavedState()?.get(key)
    }

    fun setPreviousParams(key: String, value: String? = null) {
        getPreviousSavedState()?.set(key, value)
    }

    /**
     * 设置viewmodel
     */
    @Composable
    abstract fun setViewModel()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化视图
     */
    @Composable
    abstract fun initView()
}