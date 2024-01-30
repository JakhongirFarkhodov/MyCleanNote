package com.example.mycleannote.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mycleannote.data.ShopItemRepositoryImpl
import com.example.mycleannote.domain.model.main.ShopItem
import com.example.mycleannote.domain.usecase.DeleteShopItemUseCase
import com.example.mycleannote.domain.usecase.EditShopItemUseCase
import com.example.mycleannote.domain.usecase.GetShopListUseCase

class MainViewModel : ViewModel() {

    private val repository:ShopItemRepositoryImpl = ShopItemRepositoryImpl

    private val getShopListUseCase: GetShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase:DeleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase:EditShopItemUseCase = EditShopItemUseCase(repository)

    val shopList:LiveData<List<ShopItem>>
        get() = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem)
    {
        deleteShopItemUseCase.deleteShopItem(shopItem = shopItem)
    }

    fun editShopItem(shopItem: ShopItem)
    {
        editShopItemUseCase.editShopItem(shopItem = shopItem)
    }

}