package com.baileytye.carttest.data

import java.math.BigDecimal
import java.util.*

fun BigDecimal.toCurrencyString() = "${Currency.getInstance(Locale.getDefault()).symbol}${this.setScale(2)}"