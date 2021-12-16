package com.baileytye.carttest.domain

import com.baileytye.carttest.data.toCurrencyString
import java.math.BigDecimal

data class PriceTotals(
    val subTotal: BigDecimal,
    val tax: BigDecimal,
    val total: BigDecimal,
) {
    val formattedSubTotal: String get() = subTotal.toCurrencyString()
    val formattedTax: String get() = tax.toCurrencyString()
    val formattedTotal : String get() = total.toCurrencyString()
}
