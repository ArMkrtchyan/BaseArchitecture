package com.example.core.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.core.shared.Inflater
import com.example.core.shared.extensions.ContextExtensions.inflater

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(callback: DiffUtil.ItemCallback<T> = getDiffCallback()) : ListAdapter<T, BaseListAdapter<T, VB>.ViewHolder>(callback) {

    private lateinit var _binding: VB
    protected abstract val inflate: Inflater<VB>
    open val isRecyclable: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = inflate(parent.inflater(), parent, false)
        return ViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.onBind(holder.mContext, position, getItem(position))
        holder.setIsRecyclable(isRecyclable)
    }

    abstract fun VB.onBind(context: Context, position: Int, item: T)
    inner class ViewHolder(val binding: VB) : BaseViewHolder(binding)

}