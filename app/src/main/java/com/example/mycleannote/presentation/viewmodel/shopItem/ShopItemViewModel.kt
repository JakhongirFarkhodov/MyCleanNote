package com.example.mycleannote.presentation.viewmodel.shopItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycleannote.data.ShopItemRepositoryImpl
import com.example.mycleannote.domain.model.main.ShopItem
import com.example.mycleannote.domain.usecase.AddShopItemUseCase
import com.example.mycleannote.domain.usecase.EditShopItemUseCase
import com.example.mycleannote.domain.usecase.GetShopItemUseCase
import com.example.mycleannote.domain.usecase.GetShopListUseCase

class ShopItemViewModel : ViewModel() {

    private val repository: ShopItemRepositoryImpl = ShopItemRepositoryImpl

    private val addShopItemUseCase: AddShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase: EditShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase: GetShopItemUseCase = GetShopItemUseCase(repository)

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount:LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseActivity = MutableLiveData<Unit>()
    val shouldCloseActivity:LiveData<Unit>
        get() = _shouldCloseActivity


    fun getShopItem(shopItemId:Int){
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(input_name: String?, intput_count: String?) {
        val name = reverseInputName(input_name)
        val count = reverseInputCount(intput_count)
        val validateValue = validateValue(name, count)

        if (validateValue)
        {
            val item = ShopItem(name = name, count = count, isEnabled = true)
            addShopItemUseCase.addShopItem(item)
            closeActivity()
        }

    }

    fun editShopItem(input_name: String?, intput_count: String?)
    {
        val name = reverseInputName(input_name)
        val count = reverseInputCount(intput_count)
        val validateValue = validateValue(name, count)

        if (validateValue)
        {
            _shopItem.value?.let {
                val item = it.copy(name, count)
                editShopItemUseCase.editShopItem(shopItem = item)
                closeActivity()
            }
        }
    }

    fun reverseInputName(){
        _errorInputName.value = false
    }

    fun reverseInputCount()
    {
        _errorInputCount.value = false
    }

    private fun validateValue(name:String, count:Int):Boolean
    {
        var result = true
        if (name.isBlank())
        {
            //TODO show error inputName
            _errorInputName.value = true
            result = false
        }
        if (count <= 0)
        {
            //TODO show error inputCount
            _errorInputCount.value = true
            result = false
        }

        return result
    }



    private fun reverseInputName(input_name: String?): String {
        return input_name?.trim() ?: ""
    }

    private fun reverseInputCount(intput_count: String?): Int {
        return try {
            intput_count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun closeActivity(){
        _shouldCloseActivity.value = Unit
    }

}