package top.aliyunm.example.common

import androidx.compose.runtime.Composable
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext

/**
 * coil全局上下文
 */
val coilContext: PlatformContext
    @Composable get() {
        return LocalPlatformContext.current
    }