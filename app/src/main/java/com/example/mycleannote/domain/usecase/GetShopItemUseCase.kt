package com.example.mycleannote.domain.usecase

import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem

class GetShopItemUseCase(private val repository: ShopItemRepository) {

    fun getShopItem(shopItemId:Int):ShopItem{
        return repository.getShopItem(shopItemId = shopItemId)
    }

}