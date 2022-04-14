package com.example.basearchitecture.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.basearchitecture.shared.Inflater
import com.example.basearchitecture.shared.extensions.ContextExtensions.inflater

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(callback: DiffUtil.ItemCallback<T> = getDiffCallback()) : ListAdapter<T, BaseListAdapter<T, VB>.ViewHolder>(callback) {

    private lateinit var _binding: VB
    protected abstract val inflate: Inflater<VB>
    open val isRecycleble: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = inflate(parent.inflater(), parent, false)
        return ViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.onBind(position, getItem(position))
        holder.setIsRecyclable(isRecycleble)
    }

    abstract fun VB.onBind(position: Int, item: T)
    inner class ViewHolder(val binding: VB) : BaseViewHolder(binding)

}
