package com.example.gemalto

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.example.gemalto.databinding.DialogFactoryBinding

class FingerprintDialog(context: Context, private val onCancel: () -> Unit) : Dialog(context), FingerprintUiController {

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private lateinit var mBinding: DialogFactoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogFactoryBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mBinding.cancel.setOnClickListener { onCancel.invoke();dismiss() }
    }

    override fun showDialog() {
        show()
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun onCancel() {
        onCancel.invoke()
        dismiss()
    }

    override fun setStatusText(text: String) {
        mBinding.title.text = text
    }
}