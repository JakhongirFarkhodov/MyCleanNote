package com.example.mycleannote.domain.usecase

import androidx.lifecycle.LiveData
import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem

class GetShopListUseCase(private val repository: ShopItemRepository) {

    fun getShopList():LiveData<List<ShopItem>>
    {
        return repository.getShopList()
    }

}