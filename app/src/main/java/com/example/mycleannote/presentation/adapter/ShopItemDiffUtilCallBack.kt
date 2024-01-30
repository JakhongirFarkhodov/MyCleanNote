package com.example.mycleannote.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mycleannote.domain.model.main.ShopItem

class ShopItemDiffUtilCallBack : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}