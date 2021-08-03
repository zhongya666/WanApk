package com.zhongya.havefun.app.widget.glide

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Util
import com.zhongya.havefun.app.util.DensityUtils.Companion.dp2px
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * 作者：zhongya666 on 2019/8/14 16:28
 * 邮箱：997934916@qq.com
 */
/**
 *
 * 加载圆角图片
 * *@author：txm
 *
 * https://www.jianshu.com/p/5c54046d2b1c
 */
class GlideRoundTransform(context: Context?, radius: Float) : BitmapTransformation() {
    private val radius: Float
    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val bitmap = pool[width, height, Bitmap.Config.ARGB_8888]
        bitmap.setHasAlpha(true)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
//        paint.shader = BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP)
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, paint)
        return bitmap
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is GlideRoundTransform) {
            return radius == obj.radius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode(), Util.hashCode(radius))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        val radiusData = ByteBuffer.allocate(4).putFloat(radius).array()
        messageDigest.update(radiusData)
    }

    companion object {
        private const val ID = "com.xiaohe.www.lib.tools.glide.GlideRoundTransform"
        private val ID_BYTES = ID.toByteArray(CHARSET)
    }

    init {
        this.radius = dp2px(context!!, radius)
    }
}