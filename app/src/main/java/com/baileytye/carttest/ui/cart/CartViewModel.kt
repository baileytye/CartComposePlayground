package com.baileytye.carttest.ui.cart

import androidx.lifecycle.viewModelScope
import com.baileytye.carttest.domain.CartItemUI
import com.baileytye.carttest.domain.PriceTotals
import com.baileytye.carttest.domain.UpdateCartUseCase
import com.baileytye.carttest.viewModel.StoreActionViewModel
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    override val store: Store<Any, List<CartItemUI>>,
    private val updateCartUseCase: UpdateCartUseCase
) : StoreActionViewModel<CartViewState, CartAction, Any, List<CartItemUI>>(CartViewState()) {

    init {
        refreshData(Unit)
    }

    override fun collectAction(action: CartAction) {
        when (action) {
            is CartAction.UpdateCart -> updateCart(action.id, action.count)
            CartAction.OrderNow -> orderNow()
            CartAction.Retry -> refreshData(Unit, forceRefresh = true)
        }
    }

    private fun orderNow() {

    }

    private fun updateCart(id: String, count: Int) {
        viewModelScope.launch {
            //Sync with network, in practice we should keep track of current state, and if network fails
            //restore that state, or show an error
            updateCartUseCase(id, count)
        }
        viewModelScope.launchSetState {
            //For simplicity just add or subtract and check for zero
            //This ideally could be done the data layer when initiating the data call, use a database to
            //store the current cart, and instantly update the local cache, that way the response would be
            //automatically updated in the UI since the Store object would emit a new value.
            //The following code is a bit hacky to prevent adding all the database code
            if (response is StoreResponse.Data) {
                val newResponse = with(response) {
                    copy(value = value.toMutableList().apply {
                        replaceAll { item ->
                            if (item.id == id) item.copy(count = count)
                            else item
                        }
                    }.also { updateTotals(calculateTotals(it)) })
                }
                copy(response = newResponse)
            } else {
                this
            }
        }
    }

    private fun calculateTotals(list: List<CartItemUI>): PriceTotals {
        val sub: BigDecimal = list.sumOf { it.priceValue * it.count.toBigDecimal() }
        val tax = sub * 0.05.toBigDecimal()
        val total = sub + tax

        return PriceTotals(subTotal = sub, tax = tax, total = total)
    }

    private fun updateTotals(totals: PriceTotals) {
        viewModelScope.launchSetState {
            copy(
                subTotal = totals.formattedSubTotal,
                total = totals.formattedTotal,
                tax = totals.formattedTax
            )
        }
    }

    override fun CartViewState.setResponse(response: StoreResponse<List<CartItemUI>>): CartViewState {
        if (response is StoreResponse.Data) {
            updateTotals(calculateTotals(response.value))
        }
        return copy(response = response)
    }
}