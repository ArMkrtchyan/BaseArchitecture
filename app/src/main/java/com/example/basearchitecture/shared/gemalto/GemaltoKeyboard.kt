package com.example.basearchitecture.shared.gemalto

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.basearchitecture.R
import com.gemalto.idp.mobile.authentication.mode.pin.PinAuthInput
import com.gemalto.idp.mobile.ui.UiModule
import com.gemalto.idp.mobile.ui.secureinput.SecureInputBuilderV2
import com.gemalto.idp.mobile.ui.secureinput.SecureInputService
import com.gemalto.idp.mobile.ui.secureinput.SecurePinpadListenerV2

@SuppressLint("ViewConstructor")
class GemaltoKeyboard constructor(
    context: Context,
    private val onPinCountChange: (Int) -> Unit, private val onPinAuth: (PinAuthInput) -> Unit,
) : FrameLayout(context), SecurePinpadListenerV2 {
    private val builder: SecureInputBuilderV2

    init {
        inflate(getContext(), R.layout.keyboard, this)
        val uiModule = UiModule.create()
        val secureInputService = SecureInputService.create(uiModule)
        builder = secureInputService.secureInputBuilderV2
        initViews(context)
    }

    private fun initViews(context: Context) {
        val darkGrayColor = Color.parseColor("#313748")
        val darkGrayHighlightedColor = Color.parseColor("#5A5F6D")
        val lightGrayColor = Color.parseColor("#626672")
        val white = Color.WHITE
        val lightGrayHighlightedColor = Color.parseColor("#81858E")
        builder.showTopScreen(false)
        builder.setScreenBackgroundColor(darkGrayColor)
        builder.setMaximumAndMinimumInputLength(4, 4)
        builder.setKeypadFrameColor(darkGrayColor)
        builder.setKeypadGridGradientColors(darkGrayColor, darkGrayColor)
        builder.setButtonPressVisibility(true)
        builder.setButtonBackgroundColor(SecureInputBuilderV2.UiControlState.NORMAL, lightGrayColor)
        builder.setButtonBackgroundColor(SecureInputBuilderV2.UiControlState.SELECTED, lightGrayHighlightedColor)
        builder.setKeyColor(SecureInputBuilderV2.UiControlState.NORMAL, white)
        builder.setOkButtonBehavior(SecureInputBuilderV2.OkButtonBehavior.NONE)
        builder.setOkButtonGradientColor(SecureInputBuilderV2.UiControlState.NORMAL, darkGrayColor, darkGrayColor)
        builder.setOkButtonGradientColor(SecureInputBuilderV2.UiControlState.DISABLED, darkGrayColor, darkGrayColor)
        builder.setOkButtonGradientColor(SecureInputBuilderV2.UiControlState.SELECTED, darkGrayHighlightedColor, darkGrayHighlightedColor)
        builder.setOkButtonTextColor(SecureInputBuilderV2.UiControlState.NORMAL, white)
        builder.setIsDeleteButtonAlwaysEnabled(true)
        builder.setDeleteButtonGradientColor(SecureInputBuilderV2.UiControlState.NORMAL, darkGrayColor, darkGrayColor)
        builder.setDeleteButtonGradientColor(SecureInputBuilderV2.UiControlState.SELECTED, darkGrayHighlightedColor, darkGrayHighlightedColor)
        builder.setDeleteButtonTextColor(SecureInputBuilderV2.UiControlState.NORMAL, white)
        builder.setDeleteButtonImage(R.drawable.ic_back)
        builder.setDeleteButtonText("")
        builder.swapOkAndDeleteButton()
        val mDialogFragment: DialogFragment = builder.buildPinpad(false, false, false, this).dialogFragment
        val mFragmentManager = (context as FragmentActivity).supportFragmentManager
        try {
            val fragmentTransaction: androidx.fragment.app.FragmentTransaction = mFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frame_layout_v2_default_full_screen, mDialogFragment)
            fragmentTransaction.commit()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace();
        }
    }

    override fun onKeyPressedCountChanged(count: Int, p1: Int) {
        onPinCountChange.invoke(count)
    }

    override fun onInputFieldSelected(p0: Int) {

    }

    override fun onOkButtonPressed() {

    }

    override fun onDeleteButtonPressed() {

    }

    override fun onFinish(pinAuthInput: PinAuthInput?, pinAuthInput2: PinAuthInput?) {
        pinAuthInput?.let { authInput -> onPinAuth.invoke(authInput) }
    }

    override fun onError(error: String?) {

    }
}