package com.example.basearchitecture.base

interface DifItem<T> {

    fun areItemsTheSame(second: T): Boolean
    fun areContentsTheSame(second: T): Boolean

}
