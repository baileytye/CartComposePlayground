package com.baileytye.carttest.data

import com.baileytye.carttest.domain.CartItemUI
import java.math.BigDecimal

data class CartItemResponse(
    val id: String,
    val count: Int,
    val name: String,
    val price: BigDecimal
)

fun CartItemResponse.toCartItemUI() = CartItemUI(
    id = id,
    name = name,
    count = count,
    price = price.toCurrencyString(),
    priceValue = price
)
