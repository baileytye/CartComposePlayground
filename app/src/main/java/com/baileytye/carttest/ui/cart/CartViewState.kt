package com.baileytye.carttest.ui.cart

import com.baileytye.carttest.data.toCurrencyString
import com.baileytye.carttest.domain.CartItemUI
import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreResponse
import java.math.BigDecimal

data class CartViewState(
    val response: StoreResponse<List<CartItemUI>> = StoreResponse.Loading(ResponseOrigin.Fetcher),
    val subTotal: String = BigDecimal.ZERO.toCurrencyString(),
    val tax: String = BigDecimal.ZERO.toCurrencyString(),
    val total: String = BigDecimal.ZERO.toCurrencyString(),
)