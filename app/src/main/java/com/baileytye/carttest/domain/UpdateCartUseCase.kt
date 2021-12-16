package com.baileytye.carttest.domain

import javax.inject.Inject

interface UpdateCartUseCase {
    suspend fun execute(id: String, count: Int)

    suspend operator fun invoke(id: String, count: Int){
        execute(id, count)
    }
}

class UpdateCartUseCaseImpl @Inject constructor(): UpdateCartUseCase {
    override suspend fun execute(id: String, count: Int) {
        //Do nothing, in practice call a repo to call the network, but we don't need it
    }
}