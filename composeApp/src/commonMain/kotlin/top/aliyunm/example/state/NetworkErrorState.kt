package top.aliyunm.example.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 网络状态管理器
 */
object NetworkErrorState {
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _requestState = MutableStateFlow<Boolean?>(null)
    val requestState: StateFlow<Boolean?> = _requestState

    fun showError(message: String) {
        _errorState.value = message
    }

    fun clearError() {
        _errorState.value = null
    }

    fun showRequestDialog(isShow: Boolean) {
        _requestState.value = isShow
    }

    fun closeRequestDialog() {
        _requestState.value = null
    }
}

@Composable
fun NetworkErrorHandler() {
    val errorState by NetworkErrorState.errorState.collectAsState()
    val requestState by NetworkErrorState.requestState.collectAsState()

    // 如果有错误信息，则显示弹窗
    if (errorState != null) {
        Popup(
            onDismissRequest = {
                NetworkErrorState.clearError()
            },
            alignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(200.dp, 200.dp).clip(RoundedCornerShape(10)).background(
                    Color(0x80000000)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(errorState ?: "Unknown error", textAlign = TextAlign.Start)
            }
        }
        LaunchedEffect(Unit) {
            delay(3000)
            NetworkErrorState.clearError()
        }
    }

    if (requestState == true) {
        Popup(
            onDismissRequest = {
                NetworkErrorState.closeRequestDialog()
            },
            alignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(200.dp, 200.dp).clip(RoundedCornerShape(10)).background(
                    Color(0x80000000)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text("加载中", textAlign = TextAlign.Start)
            }
        }
    }
}