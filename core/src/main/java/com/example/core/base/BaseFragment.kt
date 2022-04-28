package com.example.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core.R
import com.example.core.shared.Inflater
import com.example.core.shared.extensions.Resources.getDrawableCompat
import com.example.core.models.EmptyModel

abstract class BaseFragment<VB : ViewBinding> : Fragment(), ActivityCallback {

    protected open val mEmptyModel: EmptyModel? = null
    protected abstract val inflate: Inflater<VB>

    protected open val toolbar: Toolbar? = null
    protected lateinit var mActivity: BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            mActivity = context
        }
    }

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::_binding.isInitialized) {
            _binding = inflate(inflater, container, false)
            _binding.initView()
        }

        toolbar?.let {
            mActivity.setSupportActionBar(it)
            mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mActivity.supportActionBar?.setHomeAsUpIndicator(requireContext().getDrawableCompat(R.drawable.ic_back_light))
        }
        return _binding.root
    }

    abstract fun VB.initView()
    override fun showServerError(message: Throwable, onRetryClick: (() -> Unit)?) {
        mActivity.showServerError(message, onRetryClick)
    }

    override fun showToast(message: String) {
        mActivity.showToast(message)
    }

    override fun showToast(resId: Int) {
        mActivity.showToast(resId)
    }

    override fun showSnackBar(message: String) {
        mActivity.showSnackBar(message)
    }

    override fun showSnackBar(resId: Int) {
        mActivity.showSnackBar(resId)
    }

    override fun hideSoftInput() {
        mActivity.hideSoftInput()
    }

    override fun onResume() {
        super.onResume()
        hideSoftInput()
    }

    protected fun onActivityResult(result: ActivityResult, onResultCanceled: ((intent: Intent?) -> Unit)? = null, onResultOk: (intent: Intent?) -> Unit) {
        when (result.resultCode) {
            Activity.RESULT_OK -> onResultOk.invoke(result.data)
            Activity.RESULT_CANCELED -> onResultCanceled?.invoke(result.data)
        }
    }

    protected fun onScreenHeightChange(onChange: (Int) -> Unit) {
        val mRootWindow = requireActivity().window
        val mRootView: View = mRootWindow.decorView.findViewById(android.R.id.content)
        mRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            val view: View = mRootWindow.decorView
            view.getWindowVisibleDisplayFrame(r) // r.left, r.top, r.right, r.bottom
            onChange.invoke(mRootView.height - r.bottom)
        }
    }
}