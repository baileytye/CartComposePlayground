package com.baileytye.carttest.data

import com.baileytye.carttest.domain.CartItemUI
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartDataModule {

    @Provides
    fun providesCartStore() : Store<Any, List<CartItemUI>> = StoreBuilder.from(
        fetcher = Fetcher.of{
            listOf<CartItemResponse>(
                CartItemResponse(id = "alsdiuh", count = 0, name = "Pizza", price = 9.0.toBigDecimal()),
                CartItemResponse(id = "vndk", count = 0, name = "Soda", price = 3.0.toBigDecimal()),
                CartItemResponse(id = "sluj", count = 0, name = "Dipping Sauce", price = 4.0.toBigDecimal())
            ).map { it.toCartItemUI() }
        }
    ).build()
}