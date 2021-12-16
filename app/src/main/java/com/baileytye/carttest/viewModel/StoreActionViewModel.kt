package com.baileytye.carttest.viewModel

import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class StoreActionViewModel<S, A, StoreKey : Any, StoreData : Any>(initialState: S) :
    ActionViewModel<S, A>(initialState) {

    abstract val store: Store<StoreKey, StoreData>

    private var refreshJob: Job? = null

    fun refreshData(key: StoreKey, forceRefresh: Boolean = false) {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            store.stream(StoreRequest.cached(key = key, refresh = forceRefresh))
                .collectAndSetState { data ->
                    setResponse(data)
                }
        }
    }

    abstract fun S.setResponse(response: StoreResponse<StoreData>): S

}