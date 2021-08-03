package com.zhongya.havefun.app.util

import android.content.Context
import android.util.TypedValue

class DensityUtils private constructor() {
    companion object {
        @JvmStatic
        fun dp2px(context: Context, dpVal: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, context.resources.displayMetrics)
        }

        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    spVal, context.resources.displayMetrics).toInt()
        }

        fun px2dp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }

        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }
    }

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}