package com.example.basearchitecture

import android.view.LayoutInflater
import com.example.basearchitecture.base.BaseActivity
import com.example.basearchitecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun ActivityMainBinding.initView() {

    }

}