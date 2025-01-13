package top.aliyunm.example.platform

import android.content.pm.PackageManager
import top.aliyunm.example.IApplication

class AndroidPermissionX: PermissionX {
    override fun checkPermission(permission: String): Boolean {
        return IApplication.context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission(permission: String, callback: (Boolean) -> Unit) {

    }

    override fun requestPermissions(
        permissions: List<String>, callback: (Map<String, Boolean>) -> Unit
    ) {

    }
}

/**
 * 获取相机权限
 */
actual fun getCameraPermission() {

}

/**
 * 获取读外部存储权限
 */
actual fun getReadExternalStoragePermission() {

}

/**
 * 获取写外部存储权限
 */
actual fun getWriteExternalStoragePermission() {

}