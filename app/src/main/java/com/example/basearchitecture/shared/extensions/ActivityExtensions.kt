package com.example.basearchitecture.shared.extensions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.example.basearchitecture.BuildConfig
import com.example.basearchitecture.base.BaseActivity
import com.example.basearchitecture.base.BaseBottomSheetDialog
import com.example.basearchitecture.base.BaseFragment
import com.example.basearchitecture.base.BaseViewModel
import com.example.basearchitecture.shared.enums.BundleKeysEnum

object ActivityExtensions {
    @JvmStatic
    inline fun <reified T : BaseActivity<*>> BaseActivity<*>.launch(bundle: Bundle? = null,
        finish: Boolean = false,
        finishAll: Boolean = false,
        enterAnim: Int = 0,
        exitAnim: Int = 0
    ) {
        startActivity(Intent(this, T::class.java).apply {
            bundle?.let {
                putExtra(BundleKeysEnum.BUNDLE.key, it)
            }
            if (finishAll) {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        })
        if (finish) {
            finish()
        }
        overridePendingTransition(enterAnim, exitAnim)
    }

    @JvmStatic
    inline fun <reified T : BaseActivity<*>> BaseFragment<*>.launch(bundle: Bundle? = null, finish: Boolean = false, enterAnim: Int = 0, exitAnim: Int = 0) {
        requireActivity().startActivity(Intent(requireActivity(), T::class.java).apply {
            bundle?.let {
                putExtra(BundleKeysEnum.BUNDLE.key, it)
            }
        })
        if (finish) {
            requireActivity().finish()
        }
        requireActivity().overridePendingTransition(enterAnim, exitAnim)
    }

    @JvmStatic
    fun Any.printLog(message: String) {
        if (BuildConfig.DEBUG) Log.i(this::class.java.simpleName + "Tag", message)
    }

    @JvmStatic
    fun AppCompatActivity.showKeyboard(editText: EditText) {
        val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    @JvmStatic
    fun Fragment.showKeyboard(editText: EditText) {
        val inputMethodManager = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}