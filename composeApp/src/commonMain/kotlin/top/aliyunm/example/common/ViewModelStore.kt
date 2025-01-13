package top.aliyunm.example.common

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

val viewModelStoreOwner: ViewModelStoreOwner
    @Composable get() {
        return LocalViewModelStoreOwner.current!!
    }