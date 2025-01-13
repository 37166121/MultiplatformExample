package top.aliyunm.example.utils

import top.aliyunm.example.platform.getCachePath
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.Source
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 创建文件: FileUtils.createFile(path)
 *
 * 保存文件: FileUtils.saveFile(path, content)
 *
 * 删除文件: FileUtils.deleteFile(path)
 *
 * 读取文件: FileUtils.readFile(path).buffer().readUtf8()
 *
 * @see FileUtils.createFile
 * @see FileUtils.saveFile
 * @see FileUtils.deleteFile
 * @see FileUtils.readFile
 */
object FileUtils {

    private val fileSystem = FileSystem.SYSTEM

    /**
     * 路径字符串转Path
     */
    fun toPath(path: String): Path {
        return path.toPath()
    }

    /**
     * 创建目录
     */
    fun createDirectories(path: String) {
        createDirectories(path.toPath())
    }

    /**
     * 创建目录
     */
    fun createDirectories(path: Path) {
        fileSystem.createDirectories(path)
    }

    /**
     * 创建文件
     */
    fun createFile(path: String): Source {
        return createFile(path.toPath())
    }

    /**
     * 创建文件
     */
    fun createFile(path: Path, reCreate: Boolean = false): Source {
        // 检查文件是否存在
        if (!fileSystem.exists(path)) {
            // 创建并写入内容
            fileSystem.write(path) {}
        } else {
            // 文件已存在
            if (reCreate) {
                // 重新创建文件
                fileSystem.write(path) {}
            }
        }
        // 返回文件
        val source = fileSystem.source(path)
        return source
    }

    /**
     * 保存文件
     */
    fun saveFile(path: String, content: String) {
        saveFile(path.toPath(), content.encodeToByteArray())
    }

    /**
     * 保存文件
     */
    fun saveFile(path: String, content: ByteArray) {
        saveFile(path.toPath(), content)
    }

    /**
     * 保存文件(写入数据)
     */
    fun saveFile(path: Path, content: ByteArray) {
        fileSystem.write(path) {
            write(content)
        }
    }

    /**
     * 保存到磁盘缓存文件夹中
     */
    @OptIn(ExperimentalUuidApi::class)
    fun saveCacheFile(content: ByteArray, suffix: String = ""): Source {
        val uuid: String = Uuid.random().toHexString()
        val path = "${getCachePath()}/$uuid${if (suffix.isNotBlank()) ".$suffix" else ""}"
        saveFile(path, content)

        val source = readFile(path)
        return source
    }

    /**
     * 删除文件
     */
    fun deleteFile(path: String) {
        deleteFile(path.toPath())
    }

    /**
     * 删除文件
     */
    fun deleteFile(path: Path) {
        fileSystem.delete(path)
    }

    /**
     * 读取文件
     */
    fun readFile(path: String): Source {
        return readFile(path.toPath())
    }

    /**
     * 读取文件
     */
    fun readFile(path: Path): Source {
        return fileSystem.source(path)
    }
}