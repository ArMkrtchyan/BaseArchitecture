package com.example.core.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.core.R
import com.example.core.databinding.LayoutEmptyBinding
import com.example.core.databinding.LayoutLoadingBinding
import com.example.core.models.EmptyModel
import com.example.core.mvi.State
import com.example.core.shared.exceptions.BadRequestException
import com.example.core.shared.exceptions.CombinedException
import com.example.core.shared.exceptions.NetworkException
import com.example.core.shared.extensions.ContextExtensions.inflater
import com.example.core.shared.extensions.Resources.getColorCompat
import com.example.core.shared.extensions.ViewExtensions.show
import com.google.android.material.snackbar.Snackbar

class StateLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val mEmptyLayout by lazy { LayoutEmptyBinding.inflate(context.inflater(), this, false) }
    private val mLoadingLayout by lazy { LayoutLoadingBinding.inflate(context.inflater(), this, false) }
    private var mEmptyModel: EmptyModel? = null

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val incomingValues = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        incomingValues.recycle()
        requestLayout()
    }

    fun setUiState(state: State?) {
        if (state is State.None) return
        removeAllViews()
        when (state) {
            is State.LoadingState -> {
                addView(mLoadingLayout.root, layoutParams)
            }
            is State.EmptyState -> {
                addView(mEmptyLayout.root, layoutParams)
                setEmpty(mEmptyModel)
            }
            is State.ErrorState -> {
                when (state.throwable) {
                    is NetworkException -> {
                        addView(mEmptyLayout.root, layoutParams)
                        setEmpty(EmptyModel("Network error", "Network error desc", R.drawable.ic_back))
                    }
                    is BadRequestException -> {
                        showSnackBar(state.throwable.localizedMessage ?: "Something went wrong.")
                    }
                    is CombinedException -> {
                        addView(mEmptyLayout.root, layoutParams)
                        showSnackBar(state.throwable.localizedMessage ?: "Something went wrong.")
                        setEmpty(EmptyModel("Server error", "Server error desc", R.drawable.ic_back))
                    }
                    else -> {
                        addView(mEmptyLayout.root, layoutParams)
                        setEmpty(EmptyModel("Server error", "Server error desc", R.drawable.ic_back))
                    }
                }
            }
            else -> Unit
        }
        requestLayout()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            .setTextColor(context.getColorCompat(android.R.color.white))
            .setBackgroundTint(context.getColorCompat(android.R.color.black))
            .show()
    }


    fun setEmpty(emptyModel: EmptyModel?) {
        mEmptyModel = emptyModel
        emptyModel?.let { model ->
            mEmptyLayout.model = model
            model.icon?.let { mEmptyLayout.icon.setImageResource(it) }
        }
    }

    fun setOnButtonClick(buttonTitle: String, onButtonClick: () -> Unit) {
        mEmptyLayout.button.apply {
            text = buttonTitle
            setOnClickListener { onButtonClick.invoke() }
            show()
        }
    }
}