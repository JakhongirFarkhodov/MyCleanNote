package com.example.mycleannote.domain.usecase

import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem

class DeleteShopItemUseCase(private val repository: ShopItemRepository) {

    fun deleteShopItem(shopItem: ShopItem)
    {
        repository.deleteShopItem(shopItem = shopItem)
    }

}