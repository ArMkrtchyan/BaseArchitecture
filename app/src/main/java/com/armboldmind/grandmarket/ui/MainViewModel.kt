package com.armboldmind.grandmarket.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.armboldmind.grandmarket.GrandMarketApp
import com.armboldmind.grandmarket.base.BaseViewModel
import com.armboldmind.grandmarket.base.UIState
import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.network.NetworkError
import com.armboldmind.grandmarket.di.Local
import com.armboldmind.grandmarket.di.Remote
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var mContext: Retrofit

    @Inject
    @Local
    lateinit var mUserRepositoryLocal: IUserRepository

    @Inject
    @Remote
    lateinit var mUserRepositoryRemote: IUserRepository

    init {
        GrandMarketApp.getInstance().mAppComponent?.authorizationComponent?.build()?.inject(this)
        Log.i("Logesxnst", mContext.toString())
        getData()
    }

     fun getData() {
        viewModelScope.launch {
            mUserRepositoryRemote.signIn().onStart {
                _uiState.postValue(UIState.LOADING)
            }.catch {
                _uiState.postValue(if (NetworkError.isNetworkError(it)) UIState.NETWORK_ERROR else UIState.SERVER_ERROR)
            }.collect {
                _uiState.postValue(UIState.SUCCESS)
            }
        }
    }

    fun setNetworkError() {
        _uiState.value = UIState.NETWORK_ERROR
    }

    fun setServerError() {
        _uiState.value = UIState.SERVER_ERROR
    }

    fun setSuccess() {
        _uiState.value = UIState.SUCCESS
    }
}