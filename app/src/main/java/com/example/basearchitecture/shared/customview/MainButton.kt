package com.example.basearchitecture.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.example.basearchitecture.R
import com.example.basearchitecture.databinding.LayoutLoadingButtonBinding
import com.example.basearchitecture.mvi.State
import com.example.basearchitecture.shared.extensions.ContextExtensions.inflater
import com.example.basearchitecture.shared.extensions.Resources.getColorCompat
import com.example.basearchitecture.shared.extensions.ViewExtensions.gone
import com.example.basearchitecture.shared.extensions.ViewExtensions.show
import com.google.android.material.snackbar.Snackbar

class MainButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var text: String = ""

    private val mBinding by lazy { LayoutLoadingButtonBinding.inflate(context.inflater(), this, false) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mBinding.root, layoutParams)
        val incomingValues = context.obtainStyledAttributes(attrs, R.styleable.MainButton)
        text = incomingValues.getString(R.styleable.MainButton_text) ?: ""
        mBinding.button.text = text
        incomingValues.recycle()
        invalidate()
        requestLayout()
    }

    private fun showLoading() {
        isEnabled = false
        mBinding.apply {
            progress.show()
            button.text = ""
        }
    }

    fun setText(@StringRes text: Int) {
        setText(context.getString(text))
    }

    fun setText(text: String) {
        mBinding.button.text = text
        this.text = text
    }

    private fun hideLoading() {
        isEnabled = true
        mBinding.apply {
            progress.gone()
            button.text = text
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            .setTextColor(context.getColorCompat(android.R.color.white))
            .setBackgroundTint(context.getColorCompat(android.R.color.black))
            .show()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:isLoading")
        fun setIsLoading(button: MainButton, state: State?) {
            if (state is State.LoadingButtonState) button.showLoading() else button.hideLoading()
            if (state is State.ErrorState) {
                button.showSnackBar(state.throwable.localizedMessage)
            }
        }

        @JvmStatic
        @BindingAdapter("app:setButtonText")
        fun setButtonText(button: MainButton, text: String) {
            button.setText(text)
        }
    }
}