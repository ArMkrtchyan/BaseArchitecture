package com.example.core.shared.extensions

import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.viewbinding.ViewBinding
import com.example.core.R
import com.example.core.base.BaseFragment
import com.example.core.shared.extensions.Resources.getColorCompat

object Spannable {

    @JvmStatic
    fun TextView.setSpannable(spannableString: SpannableString) {
        text = spannableString
        highlightColor = context.getColorCompat(R.color.white)
        movementMethod = LinkMovementMethod.getInstance()
    }

    @JvmStatic
    fun BaseFragment<*>.setClickableSpan(@ColorRes color: Int, isUnderLine: Boolean = true, onClick: () -> Unit): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = isUnderLine
                ds.typeface = Typeface.DEFAULT_BOLD
                ds.color = requireContext().getColorCompat(color)
            }
        }
    }

    @JvmStatic
    fun ViewBinding.setClickableSpan(@ColorRes color: Int, onClick: () -> Unit): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.typeface = Typeface.DEFAULT_BOLD
                ds.color = root.context.getColorCompat(color)
            }
        }
    }
}