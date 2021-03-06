package com.armboldmind.grandmarket.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.armboldmind.grandmarket.base.BaseViewModel
import com.armboldmind.grandmarket.base.UIState
import com.armboldmind.grandmarket.data.IUserRepository
import com.armboldmind.grandmarket.data.network.NetworkError
import com.armboldmind.grandmarket.di.qualifiers.Local
import com.armboldmind.grandmarket.di.qualifiers.Remote
import com.armboldmind.grandmarket.shared.globalextensions.appComponent
import com.armboldmind.grandmarket.shared.managers.DataStoreManager
import com.armboldmind.grandmarket.shared.managers.PreferencesManager
import com.armboldmind.grandmarket.shared.utils.AppConstants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    @Local
    lateinit var mUserRepositoryLocal: IUserRepository

    @Inject
    @Remote
    lateinit var mUserRepositoryRemote: IUserRepository

    @Inject
    lateinit var mPreferencesManager: DataStoreManager

    val adapter = Adapter()

    init {
        appComponent().authorizationComponent.build().inject(this)
        getData()

    }

    fun getData() {
        viewModelScope.launch {
            mPreferencesManager.saveByKey(AppConstants.LANGUAGE_CODE, "hy")
            mUserRepositoryRemote.signInAsGuest().onStart {
                _uiState.postValue(UIState.LOADING)
            }.catch {
                _uiState.postValue(if (NetworkError.isNetworkError(it)) UIState.NETWORK_ERROR else UIState.SERVER_ERROR)
            }.collectLatest {
                adapter.addLoadStateListener { state ->
                    if (state.refresh is LoadState.Error) _uiState.postValue(UIState.SERVER_ERROR)
                    else if (state.prepend.endOfPaginationReached) if (adapter.itemCount == 0) _uiState.postValue(UIState.EMPTY)
                    else _uiState.postValue(UIState.SUCCESS)
                    else _uiState.postValue(UIState.LOADING)
                }
                adapter.submitData(it)
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