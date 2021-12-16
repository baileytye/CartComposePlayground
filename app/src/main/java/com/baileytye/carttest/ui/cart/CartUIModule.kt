package com.baileytye.carttest.ui.cart

import com.baileytye.carttest.domain.UpdateCartUseCase
import com.baileytye.carttest.domain.UpdateCartUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CartUIModule {
    @Binds
    abstract fun bindsUpdateCartUseCase(updateCartUseCase: UpdateCartUseCaseImpl) : UpdateCartUseCase
}