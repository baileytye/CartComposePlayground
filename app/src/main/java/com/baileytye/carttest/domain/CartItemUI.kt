package com.baileytye.carttest.domain

import java.math.BigDecimal

data class CartItemUI(
    val id: String,
    val name : String,
    val count: Int,
    /**
     * Formatted price
     */
    val price: String,
    val priceValue: BigDecimal,
)