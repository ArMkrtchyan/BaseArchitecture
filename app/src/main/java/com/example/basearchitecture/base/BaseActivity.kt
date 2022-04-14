package com.example.basearchitecture.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.basearchitecture.R
import com.example.basearchitecture.shared.exceptions.BadRequestException
import com.example.basearchitecture.shared.extensions.Resources.getColorCompat
import com.example.basearchitecture.shared.extensions.Resources.getDrawableCompat
import com.example.exception_catcher.ExceptionHandler
import com.google.android.material.snackbar.Snackbar
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), ActivityCallback {

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding
    protected open val toolbar: Toolbar? = null
    protected abstract val inflate: (LayoutInflater) -> VB
    abstract fun VB.initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> setTheme(R.style.AppThemeLight)
            Configuration.UI_MODE_NIGHT_YES -> setTheme(R.style.AppThemeDark)
        }
        super.onCreate(savedInstanceState)
        if (!::_binding.isInitialized) {
            _binding = inflate(layoutInflater)
        }
        setContentView(_binding.root)
        _binding.initView()
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> supportActionBar?.setHomeAsUpIndicator(getDrawableCompat(R.drawable.ic_back_light))
                Configuration.UI_MODE_NIGHT_YES -> supportActionBar?.setHomeAsUpIndicator(getDrawableCompat(R.drawable.ic_back))
                else -> supportActionBar?.setHomeAsUpIndicator(getDrawableCompat(R.drawable.ic_back_light))
            }
        }
        ExceptionHandler.setExceptionHandler()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showServerError(message: Throwable, onRetryClick: (() -> Unit)?
    ) {
        if (message is SocketTimeoutException || message is UnknownHostException) {
            showSnackBar(message.localizedMessage ?: "")
        } else if (message is BadRequestException) {
            showSnackBar(message.localizedMessage ?: "")
        } else {
            showSnackBar(message.localizedMessage ?: "")
        }
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setTextColor(getColorCompat(android.R.color.white))
            .setBackgroundTint(getColorCompat(android.R.color.black))
            .show()
    }

    override fun showSnackBar(@StringRes resId: Int) {
        showSnackBar(resources.getString(resId))
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(resId: Int) {
        showToast(resources.getString(resId))
    }

    override fun hideSoftInput() {
        val view: View? = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}