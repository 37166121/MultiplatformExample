package top.aliyunm.example.model

import org.jetbrains.compose.resources.DrawableResource

data class NavModel(
    val title: String,
    val icon: DrawableResource,
    val router: String
)
