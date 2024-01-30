package com.example.mycleannote.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mycleannote.domain.ShopItemRepository
import com.example.mycleannote.domain.model.main.ShopItem
import com.example.mycleannote.domain.model.main.ShopItem.Companion.UNDEFINED_ID

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopList = mutableListOf<ShopItem>()
    private val _shopListML = MutableLiveData<List<ShopItem>>()
    private var count = 0

    init {
        for (i in 0..100)
        {
            addShopItem(
                shopItem = ShopItem(
                    name = "Item ${i}",
                    count = i,
                    isEnabled = true
                )
            )
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == UNDEFINED_ID)
        {
            shopItem.id = count++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val item = getShopItem(shopItem.id)
        val index = shopList.indexOf(item)
        shopList[index] = shopItem
        updateList()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw Exception("Cannot find shop item this id:${shopItemId}")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return _shopListML
    }

    private fun updateList(){
        _shopListML.value = shopList.toList()
    }
}