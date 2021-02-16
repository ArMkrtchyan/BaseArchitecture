package com.armboldmind.grandmarket.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.armboldmind.grandmarket.R
import com.armboldmind.grandmarket.base.BaseActivity
import com.armboldmind.grandmarket.databinding.ActivityMainBinding
import com.armboldmind.grandmarket.shared.customview.StateLayout

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val mViewModel: MainViewModel by lazy { createViewModel(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            viewModel = mViewModel
            emptyModel = StateLayout.EmptyModel(R.drawable.ic_network_error, R.string.default_empty_title, R.string.default_empty_description)
            stateLayout.setOnEmptyButtonClick(R.string.done) {
                mViewModel.setNetworkError()
            }
            stateLayout.setOnNetworkErrorButtonClick(R.string.retry) {
                mViewModel.setServerError()
            }
            stateLayout.setOnServerErrorButtonClick(R.string.retry) {
                mViewModel.getData()
            }
        }
    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
}