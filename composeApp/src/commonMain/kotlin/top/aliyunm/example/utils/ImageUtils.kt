package top.aliyunm.example.utils

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImageModelEqualityDelegate
import coil3.compose.AsyncImagePainter
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import top.aliyunm.example.Ext.toPath
import top.aliyunm.example.platform.getLocalPath
import top.aliyunm.example.common.coilContext
import okio.Path

/**
 * 图片加载工具
 */
object ImageUtils {

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    fun s(path: Path) {
        val x = AsyncImagePainter.Input(loadImageLoader(), loadImageRequest(""), AsyncImageModelEqualityDelegate.Default)

    }

    fun byteArray2Bitmap(byteArray: ByteArray) {
        val pixelMap: ArrayList<Int> = arrayListOf()
        byteArray.mapIndexed { index, byte ->
            pixelMap.add(byte.toInt())
        }
        pixelMap.toIntArray()
    }

    @Composable
    fun loadImageRequest(url: Any): ImageRequest {
        val imageRequest = ImageRequest.Builder(coilContext)
            .data(url)
            .build()
        return imageRequest
    }

    @Composable
    fun loadImageLoader(): ImageLoader {
        // 内存缓存
        val memoryCacheBuilder = MemoryCache.Builder()
            .maxSizePercent(coilContext, 0.1) // 最大内存缓存大小为总内存的10%
            .build()

        // 磁盘缓存
        val diskCacheBuilder = DiskCache.Builder()
            .directory(getLocalPath().plus("image_cache").toPath())
            .maxSizePercent(0.01) // 最大磁盘缓存大小为磁盘的1%
            .build()

        val imageLoader = ImageLoader.Builder(coilContext)
            .memoryCache { memoryCacheBuilder }
            .diskCache { diskCacheBuilder }
            .build()

        return imageLoader
    }
}