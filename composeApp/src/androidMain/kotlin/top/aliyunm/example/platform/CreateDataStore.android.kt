package top.aliyunm.example.platform

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import top.aliyunm.example.IApplication

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(producePath = {
    context.filesDir.resolve(dataStoreFileName).absolutePath
})

actual val dataStore: DataStore<Preferences>
    get() = createDataStore(IApplication.context)