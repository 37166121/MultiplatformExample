package top.aliyunm.example.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import top.aliyunm.example.platform.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 数据持久化类
 */
object DataStoreUtils {

    private lateinit var _dataStore: DataStore<Preferences>
    private val mutex = Mutex()
    private val lock = Any()

    fun init() {
        _dataStore = dataStore
    }

    fun setIntData(key: String, value: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[intPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setDoubleData(
        key: String, value: Double, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[doublePreferencesKey(key)] = value
                }
            }
        }
    }

    fun setStringData(
        key: String, value: String, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[stringPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setBooleanData(
        key: String, value: Boolean, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[booleanPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setFloatData(key: String, value: Float, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[floatPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setLongData(key: String, value: Long, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[longPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setStringSetData(
        key: String, value: Set<String>, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[stringSetPreferencesKey(key)] = value
                }
            }
        }
    }

    fun setByteArrayData(
        key: String, value: ByteArray, dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it[byteArrayPreferencesKey(key)] = value
                }
            }
        }
    }

    fun getIntData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (Int?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[intPreferencesKey(key)]
                }.collect {
                    callback(it)
                }
            }
        }
    }

    fun getDoubleData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (Double?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[doublePreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getStringData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (String?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[stringPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getBooleanData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (Boolean?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[booleanPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getFloatData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (Float?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[floatPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getLongData(
        key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO, callback: (Long?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[longPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getStringSetData(
        key: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        callback: (Set<String>?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[stringSetPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun getByteArrayData(
        key: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        callback: (ByteArray?) -> Unit
    ) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.data.map {
                    it[byteArrayPreferencesKey(key)]
                }.collect({
                    callback(it)
                })
            }
        }
    }

    fun removeInt(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(intPreferencesKey(key))
                }
            }
        }
    }

    fun removeDouble(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(doublePreferencesKey(key))
                }
            }
        }
    }

    fun removeString(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(stringPreferencesKey(key))
                }
            }
        }
    }

    fun removeBoolean(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(booleanPreferencesKey(key))
                }
            }
        }
    }

    fun removeFloat(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(floatPreferencesKey(key))
                }
            }
        }
    }

    fun removeLong(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(longPreferencesKey(key))
                }
            }
        }
    }

    fun removeStringSet(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(stringSetPreferencesKey(key))
                }
            }
        }
    }

    fun removeByteArray(key: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        CoroutineScope(dispatcher).launch {
            mutex.withLock(lock) {
                _dataStore.edit {
                    it.remove(byteArrayPreferencesKey(key))
                }
            }
        }
    }
}