package com.example.exception_catcher

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.exception_catcher.databinding.ActivityExceptionBinding

internal class ExceptionActivity : AppCompatActivity() {

    private val _binding by lazy { ActivityExceptionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.handleWindowState()
        setContentView(_binding.root)

        _binding.restartApplication.setOnClickListener {
            val appPackage = intent.getStringExtra("packageName")
            if (appPackage != null) {
                val i = applicationContext.packageManager.getLaunchIntentForPackage(appPackage)
                i?.data = Uri.parse(appPackage)
                startActivity(i)
                finish()
            }
        }

        val model = intent.getParcelableExtra<ErrorModel>("model")
        Log.e("ErrorTag", model.toString())
    }
}