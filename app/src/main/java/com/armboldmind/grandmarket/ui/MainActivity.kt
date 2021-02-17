package com.armboldmind.grandmarket.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.armboldmind.grandmarket.R
import com.armboldmind.grandmarket.base.BaseActivity
import com.armboldmind.grandmarket.base.BasePagingAdapter
import com.armboldmind.grandmarket.databinding.ActivityMainBinding
import com.armboldmind.grandmarket.shared.customview.StateLayout
import com.armboldmind.grandmarket.shared.utils.AppConstants
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val mViewModel: MainViewModel by lazy { createViewModel(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (mViewModel.mPreferencesManager.findByKey<Boolean>("DarkMode")) theme.applyStyle(R.style.AppThemeDark, true) else theme.applyStyle(R.style.AppThemeLight, true)
        updateResources(mViewModel.mPreferencesManager.findByKey(AppConstants.LANGUAGE_CODE) ?: "en")
        super.onCreate(savedInstanceState)
        mBinding.apply {
            viewModel = mViewModel
            emptyModel = StateLayout.EmptyModel(R.drawable.ic_network_error, R.string.default_empty_title, R.string.default_empty_description)
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
                Log.i("TAg", Locale.getDefault().language)
            }
            darkMode.isChecked = mViewModel.mPreferencesManager.findByKey("DarkMode") ?: false
            darkMode.setOnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.mPreferencesManager.saveByKey("DarkMode", isChecked)
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
}