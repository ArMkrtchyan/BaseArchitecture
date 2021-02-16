package com.armboldmind.grandmarket.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseView {

    protected lateinit var mActivity: BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            mActivity = context
        }
    }

    inline fun <reified T : BaseViewModel> createViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    protected abstract val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate(inflater, container, false)
        return _binding.root
    }

    override fun showServerError(message: String) {
        mActivity.showServerError(message)
    }

    override fun showServerError(@StringRes resId: Int) {
        showServerError(resources.getString(resId))
    }

    override fun showNetworkErrorSnackBar(message: String) {
        mActivity.showNetworkErrorSnackBar(message)
    }

    override fun showNetworkErrorSnackBar(@StringRes resId: Int) {
        showNetworkErrorSnackBar(resources.getString(resId))
    }

    override fun showToast(message: String) {
        mActivity.showToast(message)
    }

    override fun showToast(@StringRes resId: Int) {
        showToast(resources.getString(resId))
    }

    override fun hasPermission(permission: String): Boolean {
        return mActivity.hasPermission(permission)
    }

    override fun hideSoftInput() {
        mActivity.hideSoftInput()
    }
}