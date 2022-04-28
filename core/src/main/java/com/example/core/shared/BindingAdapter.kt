package com.example.core.shared

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.core.mvi.State
import com.example.core.shared.customview.MainButton
import com.example.core.shared.customview.StateLayout
import com.example.core.models.EmptyModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("state")
    fun setStateLayoutState(stateLayout: StateLayout, state: State?) {
        stateLayout.setUiState(state)
    }

    @JvmStatic
    @BindingAdapter("enabled")
    fun isEnabled(view: View, state: State?) {
        view.isEnabled = state !is State.LoadingButtonState
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, state: State?) {
        view.isVisible = state is State.Success
    }

    @JvmStatic
    @BindingAdapter("setMainButtonText")
    fun setMainButtonText(mainButton: MainButton, text: String) {
        mainButton.setText(text)
    }

    @JvmStatic
    @BindingAdapter("emptyModel")
    fun setEmpty(stateLayout: StateLayout, emptyModel: EmptyModel) {
        stateLayout.setEmpty(emptyModel)
    }

    @JvmStatic
    @BindingAdapter("mustShow")
    fun mustShow(view: View, isMustShow: Boolean) {
        view.isVisible = isMustShow
    }

    @JvmStatic
    @BindingAdapter("setHtml")
    fun setHtml(textView: TextView, key: String?) {
        key?.let { textView.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).trimEnd() }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("isUnderLine")
    fun isUnderLine(view: TextView, isUnderLine: Boolean) {
        if (isUnderLine) view.paintFlags = view.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}