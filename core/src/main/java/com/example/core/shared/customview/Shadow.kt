package com.example.core.shared.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.core.R

class Shadow @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mRadius: Float
    private val mShadowRadius: Float
    private val mPadding: Float
    private var mRect = RectF()
    private val mIsCycle: Boolean
    private val mMaskFilter: BlurMaskFilter
    private val mPath = Path()
    private val mPaint: Paint

    init {
        val incomingValues = context.obtainStyledAttributes(attrs, R.styleable.Shadow)
        mRadius = incomingValues.getDimensionPixelSize(R.styleable.Shadow_cornerRadius, 10)
            .toFloat()
        mIsCycle = incomingValues.getBoolean(R.styleable.Shadow_isCycle, false)
        mShadowRadius = incomingValues.getFloat(R.styleable.Shadow_shadowRadius, 15f)
        mPadding = mShadowRadius + 5f
        mMaskFilter = BlurMaskFilter(mShadowRadius, BlurMaskFilter.Blur.NORMAL)
        mPaint = Paint().apply {
            strokeWidth = 10f
            color = incomingValues.getColor(R.styleable.Shadow_shadowColor, ContextCompat.getColor(context, R.color.shadow_color))
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
            maskFilter = mMaskFilter
        }
        incomingValues.recycle()
        invalidate()
        requestLayout()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect = RectF(mPadding, mPadding, w.toFloat() - mPadding, h.toFloat() - mPadding)
        if (!mIsCycle) mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CW)
        else mPath.addCircle((w / 2).toFloat(), (h / 2).toFloat(), if (w < h) (w / 2).toFloat() - mPadding else (h / 2).toFloat() - mPadding, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawPath(mPath, mPaint)
        }
    }
}