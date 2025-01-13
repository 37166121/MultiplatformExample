package top.aliyunm.example.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * 生命周期
 */
val lifecycleOwner: LifecycleOwner
    @Composable get() {
        return LocalLifecycleOwner.current
    }