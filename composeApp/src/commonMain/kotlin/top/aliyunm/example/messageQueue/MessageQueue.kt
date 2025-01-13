package top.aliyunm.example.messageQueue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 消息队列
 */
class MessageQueue<T> {
    /**
     * 消息列表
     */
    private val queue = mutableListOf<T>()

    /**
     * 互斥锁
     */
    private val mutex = Mutex()

    /**
     * 生产消息
     */
    fun produce(message: T) {
        CoroutineScope(Dispatchers.IO).launch {
            mutex.withLock {
                queue.add(message)
            }
        }
    }

    /**
     * 消费消息
     */
    suspend fun consume(): T {
        return mutex.withLock {
            while (queue.isEmpty()) {
                delay(10)
            }
            val message = queue.removeAt(0)
            message
        }
    }
}