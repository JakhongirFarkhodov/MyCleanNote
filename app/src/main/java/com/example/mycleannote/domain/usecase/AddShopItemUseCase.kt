package com.example.mycleannote.domain.usecase

import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem

class AddShopItemUseCase(private val repository: ShopItemRepository) {

    fun addShopItem(shopItem: ShopItem)
    {
        repository.addShopItem(shopItem = shopItem)
    }

}