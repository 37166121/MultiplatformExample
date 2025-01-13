package top.aliyunm.example.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ToastState {

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState: StateFlow<String?> = _messageState

    fun showToast(message: String) {
        clearToast()
        _messageState.value = message
    }

    fun clearToast() {
        _messageState.value = null
    }
}

@Composable
fun Toast() {
    val messageState by ToastState.messageState.collectAsState()

    if (messageState != null) {
        Popup(
            onDismissRequest = {
                ToastState.clearToast()
            },
            properties = PopupProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            alignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.7f).height(75.dp).padding(10.dp).shadow(10.dp)
                    .clip(RoundedCornerShape(10.dp)).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    messageState ?: "",
                    modifier = Modifier.padding(5.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        LaunchedEffect(Unit) {
            delay(3000)
            ToastState.clearToast()
        }
    }
}