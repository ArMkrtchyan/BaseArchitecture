package com.armboldmind.grandmarket.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.armboldmind.grandmarket.R
import com.armboldmind.grandmarket.base.UIState
import com.armboldmind.grandmarket.databinding.LayoutEmptyViewBinding
import com.armboldmind.grandmarket.databinding.LayoutErrorViewBinding
import com.armboldmind.grandmarket.databinding.LayoutLoadingBinding
import com.armboldmind.grandmarket.shared.globalextensions.inflater
import com.armboldmind.grandmarket.shared.globalextensions.show

class StateLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {

        @JvmStatic
        @BindingAdapter("app:setStateHandler")
        fun setStateHandler(view: StateLayout, stateHandler: LiveData<UIState>) {
            view._setStateHandler(stateHandler)
        }

        @JvmStatic
        @BindingAdapter("app:setEmpty")
        fun setEmpty(view: StateLayout, emptyModel: EmptyModel?) {
            view.setEmpty(emptyModel)
        }
    }

    private val mLoadingBinding: LayoutLoadingBinding by lazy { LayoutLoadingBinding.inflate(context.inflater(), this, false) }
    private val mErrorBinding: LayoutErrorViewBinding by lazy {
        LayoutErrorViewBinding.inflate(context.inflater(), this, false).apply {
            viewImage.setImageResource(R.drawable.ic_server_error);viewImage.show()
            title.text = context.getString(R.string.default_server_error_title);title.show()
            subtitle.text = context.resources.getString(R.string.default_server_error_description);subtitle.show()
        }
    }

    private val mNetworkErrorBinding: LayoutErrorViewBinding by lazy {
        LayoutErrorViewBinding.inflate(context.inflater(), this, false).apply {
            viewImage.setImageResource(R.drawable.ic_network_error);viewImage.show()
            title.text = context.getString(R.string.default_network_error_title);title.show()
            subtitle.text = context.resources.getString(R.string.default_network_error_description);subtitle.show()
        }
    }
    private val mEmptyBinding: LayoutEmptyViewBinding by lazy { LayoutEmptyViewBinding.inflate(context.inflater(), this, false) }
    private var stateLiveData: LiveData<UIState>? = null
    private var mState: UIState? = null
    private val stateObserver = Observer<UIState> { state ->
        removeAllViews()
        state?.let {
            mState = it
            when (it) {
                UIState.SERVER_ERROR, UIState.INTERNAL_ERROR -> {
                    addView(mErrorBinding.root, layoutParams)
                }
                UIState.NETWORK_ERROR -> {
                    addView(mNetworkErrorBinding.root, layoutParams)
                }
                UIState.LOADING -> {
                    addView(mLoadingBinding.root, layoutParams)
                }
                UIState.SUCCESS -> {
                }
                UIState.EMPTY -> {
                    addView(mEmptyBinding.root, layoutParams)
                }
            }
        }
    }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val incomingValues = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        incomingValues.recycle()
        requestLayout()
    }

    private fun _setStateHandler(liveData: LiveData<UIState>) {
        stateLiveData = liveData
        stateLiveData?.observeForever(stateObserver)
    }

    fun setEmpty(emptyModel: EmptyModel?) {
        emptyModel?.let { model ->
            model.emptyDescription?.let { mEmptyBinding.subtitle.text = context.getString(it);mEmptyBinding.subtitle.show() }
            model.emptyTitle?.let { mEmptyBinding.title.text = context.getString(it);mEmptyBinding.title.show() }
            model.imageRes?.let { mEmptyBinding.viewImage.setImageResource(it);mEmptyBinding.viewImage.show() }
        }
    }

    fun setOnEmptyButtonClick(@StringRes buttonTitle: Int, onButtonClick: () -> Unit) {
        mEmptyBinding.apply {
            btn.text = context.getString(buttonTitle)
            btn.setOnClickListener { onButtonClick.invoke() }
            btn.show()
        }
    }

    fun setOnServerErrorButtonClick(@StringRes buttonTitle: Int, onButtonClick: () -> Unit) {
        mErrorBinding.apply {
            btn.text = context.getString(buttonTitle)
            btn.setOnClickListener { onButtonClick.invoke() }
            btn.show()
        }
    }

    fun setOnNetworkErrorButtonClick(@StringRes buttonTitle: Int, onButtonClick: () -> Unit) {
        mNetworkErrorBinding.apply {
            btn.text = context.getString(buttonTitle)
            btn.setOnClickListener { onButtonClick.invoke() }
            btn.show()
        }
    }

    data class EmptyModel(
        @DrawableRes val imageRes: Int? = null,
        @StringRes val emptyTitle: Int? = null,
        @StringRes val emptyDescription: Int? = null,
    )
}