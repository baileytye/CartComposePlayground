package com.baileytye.carttest.ui.cart

sealed class CartAction {
    data class UpdateCart(val id: String, val count: Int) : CartAction()
    object OrderNow : CartAction()
    object Retry: CartAction()
}