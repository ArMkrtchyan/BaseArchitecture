package com.example.basearchitecture.shared.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.FrameMetricsAggregator
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.basearchitecture.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ViewExtensions {
    @JvmStatic
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

    @JvmStatic
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

    @JvmStatic
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

    @JvmStatic
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

    @JvmStatic
    fun View.expandHeight(
        fromHeight: Int, toHeight: Int,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        interpolator: TimeInterpolator? = AccelerateDecelerateInterpolator(),
        endAction: (() -> Unit)? = null,
    ) {
        this.measure(View.MeasureSpec.makeMeasureSpec(this.rootView.width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(this.rootView.height, View.MeasureSpec.EXACTLY))

        val widthAnimator = ValueAnimator.ofInt(fromHeight, toHeight)
        widthAnimator.addUpdateListener { animation ->
            this.layoutParams.height = animation.animatedValue as Int
            this.requestLayout()
            if (animation.animatedValue == toHeight) {
                endAction?.invoke()
            }
        }
        widthAnimator.duration = duration
        widthAnimator.interpolator = interpolator
        widthAnimator.start()
    }

    @JvmStatic
    fun View.getMeasure(): Pair<Int, Int> {
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width = measuredWidth
        val height = measuredHeight
        return width to height
    }

    @JvmStatic
    fun View.animateVertically(
        target: Float,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        endAction: (() -> Unit)? = null,
    ) {
        this.animate().y(target).setDuration(duration).withEndAction {
            endAction?.invoke()
        }.start()
    }

    @JvmStatic
    fun View.animateHorizontally(
        target: Float,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        endAction: (() -> Unit)? = null,
    ) {
        this.animate().x(target).setDuration(duration).withEndAction {
            endAction?.invoke()
        }.start()
    }

    @JvmStatic
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

    @JvmStatic
    fun View.animateScaleY(
        target: Float,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        startAction: (View.() -> Unit)? = null,
        endAction: (View.() -> Unit)? = null,
    ) {
        startAction?.invoke(this)
        animate().scaleY(target).setDuration(duration).withEndAction {
            endAction?.invoke(this)
        }.start()
    }

    @JvmStatic
    fun View.animateScaleX(
        target: Float,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        startAction: (View.() -> Unit)? = null,
        endAction: (View.() -> Unit)? = null,
    ) {
        startAction?.invoke(this)
        animate().scaleX(target).setDuration(duration).withEndAction {
            endAction?.invoke(this)
        }.start()
    }


    @JvmStatic
    fun View.animateRotation(
        target: Float,
        duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
        interpolator: TimeInterpolator? = AccelerateInterpolator(),
        startAction: (View.() -> Unit)? = null,
        endAction: (View.() -> Unit)? = null,
    ) {
        startAction?.invoke(this)
        animate().rotation(target).setDuration(duration).setInterpolator(interpolator).withEndAction {
            endAction?.invoke(this)
        }.start()
    }

    @JvmStatic
    fun View.gone() {
        visibility = View.GONE
    }

    @JvmStatic
    fun View.invisible() {
        visibility = View.INVISIBLE
        setOnClickListener(null)
    }

    @JvmStatic
    fun View.show() {
        visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    fun TextView.startTimer(lifecycleScope: CoroutineScope, onTimerFinish: () -> Unit) {
        lifecycleScope.launch {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setOnClickListener(null)
            requestLayout()
            for (seconds in 59 downTo 1) {
                text = if (seconds > 9) " 00:$seconds"
                else " 00:0$seconds"
                delay(1000)
            }

            setOnClickListener {
                onTimerFinish.invoke()
                text = ""
            }
        }
    }

    @JvmStatic
    fun View.toTransitionGroup() = this to transitionName

    @JvmStatic
    fun CardView.setDimensionRatio(ratio: String) {
        updateLayoutParams<ConstraintLayout.LayoutParams> { dimensionRatio = ratio }
    }

    @JvmStatic
    fun ImageView.load(url: String, @DrawableRes errorIcon: Int = R.drawable.ic_back, isCircle: Boolean = false, onResourceReady: (() -> Unit)? = null
    ) {
        Glide.with(context).load(url).apply(if (isCircle) RequestOptions().circleCrop() else RequestOptions().dontTransform()).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                setImageResource(errorIcon)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {
                setImageDrawable(resource)
                onResourceReady?.invoke()
                return false
            }
        }).into(this)
    }

    @JvmStatic
    fun RangeSlider.onStopTrackingTouch(values: (from: Long, to: Long) -> Unit) {
        addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                values.invoke(slider.values[0].toLong(), slider.values[1].toLong())
            }

        })
    }

    @JvmStatic
    fun BottomSheetBehavior<*>.onSlide(alpha: (alpha: Float) -> Unit) {
        addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val offset = 1 - slideOffset
                alpha.invoke(slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }
        })
    }

    @JvmStatic
    fun BottomSheetBehavior<*>.onState(alpha: (state: Int) -> Unit) {
        addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                alpha.invoke(newState)
            }
        })
    }
}