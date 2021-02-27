package com.armboldmind.grandmarket.ui

import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.armboldmind.grandmarket.R
import com.armboldmind.grandmarket.base.BaseActivity
import com.armboldmind.grandmarket.base.BasePagingAdapter
import com.armboldmind.grandmarket.databinding.ActivityMainBinding
import com.armboldmind.grandmarket.shared.customview.StateLayout
import com.armboldmind.grandmarket.shared.globalextensions.getColorCompat
import com.armboldmind.grandmarket.shared.utils.AppConstants
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val mViewModel: MainViewModel by lazy { createViewModel(MainViewModel::class.java) }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override val mEmptyModel: StateLayout.EmptyModel?
        get() = StateLayout.EmptyModel(R.drawable.ic_network_error, R.string.default_empty_title, R.string.default_empty_description)

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            updateResources(mViewModel.mPreferencesManager.findByKey(AppConstants.LANGUAGE_CODE).firstOrNull() ?: "en")
            if (mViewModel.mPreferencesManager.findByKey(AppConstants.DARK_MODE).first() == true) theme.applyStyle(R.style.AppThemeDark, true) else theme.applyStyle(R.style.AppThemeLight,
                true)
        }
        super.onCreate(savedInstanceState)
        mBinding.apply {
            viewModel = mViewModel
            emptyModel = mEmptyModel
            list.adapter = mViewModel.adapter.withLoadStateFooter(BasePagingAdapter.LoadStateAdapter {
                mViewModel.adapter.retry()
            })
            stateLayout.setOnEmptyButtonClick(R.string.done) {
                mViewModel.setNetworkError()
            }
            stateLayout.setOnNetworkErrorButtonClick(R.string.retry) {
                mViewModel.setServerError()
            }
            stateLayout.setOnServerErrorButtonClick(R.string.retry) {
                mViewModel.getData()
            }
            lifecycleScope.launch {
                mViewModel.mPreferencesManager.findByKey(AppConstants.DARK_MODE).asLiveData().observe(this@MainActivity) {
                    darkMode.isChecked = it ?: false
                }
            }
        }
    }

    private fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            circularProgressDrawable.colorFilter = BlendModeColorFilter(getColorCompat(R.color.colorAccent), BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION") circularProgressDrawable.setColorFilter(getColorCompat(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
        }
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    override fun setListeners(binding: ActivityMainBinding) {
        binding.darkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            lifecycleScope.launch {
                mViewModel.mPreferencesManager.saveByKey(AppConstants.DARK_MODE, isChecked)
                startActivity(Intent(this@MainActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                overridePendingTransition(0, 0)
                finish()
            }
        }
    }

    override fun removeListeners(binding: ActivityMainBinding) {
        binding.darkMode.setOnCheckedChangeListener(null)
    }
}