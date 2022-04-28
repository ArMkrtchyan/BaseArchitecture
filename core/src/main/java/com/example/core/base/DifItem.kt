package com.example.core.base

interface DifItem<T> {

    fun areItemsTheSame(second: T): Boolean
    fun areContentsTheSame(second: T): Boolean

}
