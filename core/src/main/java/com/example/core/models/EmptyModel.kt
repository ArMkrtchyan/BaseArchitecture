package com.example.core.models

import androidx.annotation.DrawableRes

data class EmptyModel(val title: String?, val description: String? = null, @DrawableRes val icon: Int? = null)
