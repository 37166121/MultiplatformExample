package top.aliyunm.example.platform

/**
 * 权限
 */
interface PermissionX {
    fun checkPermission(permission: String): Boolean
    fun requestPermission(permission: String, callback: (Boolean) -> Unit)
    fun requestPermissions(permissions: List<String>, callback: (Map<String, Boolean>) -> Unit)
}

// 预设权限申请

/**
 * 获取相机权限
 */
expect fun getCameraPermission()

/**
 * 获取读取外部存储权限
 */
expect fun getReadExternalStoragePermission()

/**
 * 获取写入外部存储权限
 */
expect fun getWriteExternalStoragePermission()
