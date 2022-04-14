package com.example.basearchitecture.shared

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB