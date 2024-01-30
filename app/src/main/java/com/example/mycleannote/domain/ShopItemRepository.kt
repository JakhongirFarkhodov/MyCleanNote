package com.example.mycleannote.domain

import androidx.lifecycle.LiveData
import com.example.mycleannote.domain.model.main.ShopItem

interface ShopItemRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId:Int):ShopItem

    fun getShopList():LiveData<List<ShopItem>>

}