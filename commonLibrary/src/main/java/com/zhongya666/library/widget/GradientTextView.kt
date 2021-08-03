package com.zhongya666.library.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by 中亚 on 2018/1/6.
 */
class GradientTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context!!, attrs) {
    private var mLinearGradient: LinearGradient? = null
    private var mGradientMatrix: Matrix? = null
    private lateinit var mPaint: Paint
    private var mViewWidth = 0f
    private var mTranslate = 0f
    private val mAnimating = true
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth == 0f) {
            //getWidth得到是某个view的实际尺寸.
            //getMeasuredWidth是得到某view想要在parent view里面占的大小.
            mViewWidth = measuredWidth.toFloat()
            if (mViewWidth > 0) {
                mPaint = paint
                //线性渐变
                mLinearGradient = LinearGradient(-mViewWidth, 0f, 0f, 0f, intArrayOf(-0xda2947, -0x7fff01, -0xda2947), floatArrayOf(0f, 1f, 0f), Shader.TileMode.MIRROR)
                mPaint.setShader(mLinearGradient)
                mGradientMatrix = Matrix()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mAnimating && mGradientMatrix != null) {
            mTranslate += mViewWidth / 10
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth
            }
            mGradientMatrix!!.setTranslate(mTranslate.toFloat(), 0f)
            mLinearGradient!!.setLocalMatrix(mGradientMatrix)
            //50ms刷新一次
            postInvalidateDelayed(500)
        }
    }
}