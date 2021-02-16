package com.armboldmind.grandmarket.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.armboldmind.grandmarket.R
import com.armboldmind.grandmarket.shared.globalextensions.getColorCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IBaseView {

    inline fun <reified T : BaseViewModel> createViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    protected abstract val inflate: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        updateResources("en")
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun showServerError(message: String) {
        showServerErrorSnackBar(message)
    }

    override fun showServerError(resId: Int) {
        showServerError(resources.getString(resId))
    }

    override fun showNetworkErrorSnackBar(message: String) {
        showNetworkSnackBar(message)
    }

    override fun showNetworkErrorSnackBar(resId: Int) {
        showNetworkErrorSnackBar(resources.getString(resId))
    }

    private fun showNetworkSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE).setTextColor(getColorCompat(android.R.color.white))
            .setBackgroundTint(getColorCompat(android.R.color.black)).setActionTextColor(getColorCompat(R.color.colorAccent)).setAction("Ok") {

            }.show()
    }

    private fun showServerErrorSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).setTextColor(getColorCompat(android.R.color.white))
            .setBackgroundTint(getColorCompat(android.R.color.black)).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(resId: Int) {
        showToast(resources.getString(resId))
    }

    override fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun hideSoftInput() {
        val view: View? = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun updateResources(language: String) {
        val res = this.resources
        val conf = res.configuration
        conf.setLocale(Locale(language))
        Locale.setDefault(Locale(language))
        this.baseContext.resources.updateConfiguration(conf, this.baseContext.resources.displayMetrics)
        ActivityCompat.invalidateOptionsMenu(this)
        this.resources.updateConfiguration(conf, this.resources.displayMetrics)
        res.updateConfiguration(conf, res.displayMetrics)
    }
}