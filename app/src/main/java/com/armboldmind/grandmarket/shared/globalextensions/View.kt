package com.armboldmind.grandmarket.shared.globalextensions

import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.widget.EditText
import androidx.core.app.FrameMetricsAggregator

fun View.expandHeight() {
    this.measure(View.MeasureSpec.makeMeasureSpec(this.rootView.width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(this.rootView.height, View.MeasureSpec.AT_MOST))
    val targetHeight = this.measuredHeight

    val heightAnimator = ValueAnimator.ofInt(0, targetHeight)
    heightAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    heightAnimator.duration = FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    heightAnimator.start()
}

fun View.collapseHeight() {
    val initialHeight = this.measuredHeight
    val heightAnimator = ValueAnimator.ofInt(0, initialHeight)
    heightAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        this.layoutParams.height = initialHeight - animatedValue
        this.requestLayout()
    }
    heightAnimator.duration = FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    heightAnimator.start()
}

fun View.collapseWidth() {
    val initialWidth = this.measuredWidth
    val widthAnimator = ValueAnimator.ofInt(0, initialWidth)
    widthAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        this.layoutParams.width = initialWidth - animatedValue
        this.requestLayout()
    }
    widthAnimator.duration = FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    widthAnimator.start()
}

fun View.expandWidth(toWidth: Int) {
    this.measure(View.MeasureSpec.makeMeasureSpec(this.rootView.width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(this.rootView.height, View.MeasureSpec.EXACTLY))

    val widthAnimator = ValueAnimator.ofInt(0, toWidth)
    widthAnimator.addUpdateListener { animation ->
        this.layoutParams.width = animation.animatedValue as Int
        this.requestLayout()
    }
    widthAnimator.duration = FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    widthAnimator.start()
}

fun View.getMeasure(): Pair<Int, Int> {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val width = measuredWidth
    val height = measuredHeight
    return width to height
}

fun View.animateVertically(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    endAction: (() -> Unit)? = null,
) {
    this.animate().y(target).setDuration(duration).withEndAction {
            endAction?.invoke()
        }.start()
}

fun View.animateHorizontally(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    endAction: (() -> Unit)? = null,
) {
    this.animate().x(target).setDuration(duration).withEndAction {
            endAction?.invoke()
        }.start()
}

fun View.animateAlpha(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    startAction: (View.() -> Unit)? = null,
    endAction: (View.() -> Unit)? = null,
) {
    startAction?.invoke(this)
    animate().alpha(target).setDuration(duration).withEndAction {
            endAction?.invoke(this)
        }.start()
}


fun View.setKeyboardListeners(vararg editText: EditText) {
    viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        getWindowVisibleDisplayFrame(r)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - r.bottom
        editText.forEach {
            it.isCursorVisible = keypadHeight > screenHeight * 0.15
        }
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
    setOnClickListener(null)
}

fun View.show() {
    visibility = View.VISIBLE
}