package com.example.basearchitecture.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder(view: ViewBinding) : RecyclerView.ViewHolder(view.root) {
     val mContext = view.root.context
}
