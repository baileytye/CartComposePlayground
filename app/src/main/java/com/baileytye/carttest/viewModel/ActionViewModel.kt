package com.baileytye.carttest.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class ActionViewModel<S, A>(initialState: S) : StateViewModel<S>(initialState) {
    private val pendingActions = MutableSharedFlow<A>()

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                collectAction(action)
            }
        }
    }

    abstract fun collectAction(action: A)

    fun submitAction(action: A) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}