package com.example.mycleannote.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mycleannote.R
import com.example.mycleannote.domain.model.main.ShopItem

class ShopItemAdapter : ListAdapter<ShopItem, ShopItemAdapter.ShopItemViewHolder>(ShopItemDiffUtilCallBack()){

    var onItemClickListener:((ShopItem) -> Unit)? = null
    var onItemLongClickListener:((ShopItem) -> Unit)? = null
    private var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType)
        {
            VIEW_ITEM_ENABLED -> R.layout.item_shop_enabled
            VIEW_ITEM_DISABLED -> R.layout.item_shop_disabled
            else -> {
                throw Exception("Cannot find layout")
            }
        }

        Log.d("TAG", "onCreateViewHolder: ${count++}")

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.tv_name.text = item.name
        holder.tv_count.text = item.count.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(item)
            true
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isEnabled){
            VIEW_ITEM_ENABLED
        }
        else{
            VIEW_ITEM_DISABLED
        }
    }

    class ShopItemViewHolder(itemView:View) : ViewHolder(itemView)
    {
        val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        val tv_count = itemView.findViewById<TextView>(R.id.tv_count)
    }


    companion object{

        const val VIEW_ITEM_ENABLED = 1
        const val VIEW_ITEM_DISABLED = 0


    }
}