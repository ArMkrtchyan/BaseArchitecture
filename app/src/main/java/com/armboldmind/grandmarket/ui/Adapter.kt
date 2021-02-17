package com.armboldmind.grandmarket.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.armboldmind.grandmarket.base.BasePagingAdapter
import com.armboldmind.grandmarket.base.BaseViewHolder
import com.armboldmind.grandmarket.data.models.responsemodels.UserResponseModel
import com.armboldmind.grandmarket.databinding.AdapterPagingBinding
import com.armboldmind.grandmarket.shared.globalextensions.inflater

class Adapter : BasePagingAdapter<UserResponseModel, Adapter.ViewHolder>(object : DiffUtil.ItemCallback<UserResponseModel>() {
    override fun areItemsTheSame(oldItem: UserResponseModel, newItem: UserResponseModel): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: UserResponseModel, newItem: UserResponseModel): Boolean {
        return oldItem.id == oldItem.id
    }

}) {
    class ViewHolder(val mBinding: AdapterPagingBinding) : BaseViewHolder(mBinding)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.id.text = getItem(position)?.id.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterPagingBinding.inflate(parent.context.inflater(), parent, false))
    }
}