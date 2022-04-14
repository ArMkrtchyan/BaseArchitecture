package com.example.basearchitecture.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.basearchitecture.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment() {
    protected abstract val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun VB.initView()
    protected open val draggable: Boolean = false
    protected open val cancelable: Boolean = true

    @SuppressLint("ResourceType")
    override fun getTheme(): Int = R.style.BottomSheetDialogThemeLight
    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog = BottomSheetDialog(requireContext(), theme).apply {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = draggable
    }

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (!::_binding.isInitialized) {
            _binding = inflate(inflater, container, false)
            _binding.initView()
        }
        isCancelable = cancelable
        return _binding.root
    }
}