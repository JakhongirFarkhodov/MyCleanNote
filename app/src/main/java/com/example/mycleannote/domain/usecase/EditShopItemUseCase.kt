package com.example.mycleannote.domain.usecase

import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem

class EditShopItemUseCase(private val repository: ShopItemRepository) {

    fun editShopItem(shopItem: ShopItem)
    {
        repository.editShopItem(shopItem = shopItem)
    }

}