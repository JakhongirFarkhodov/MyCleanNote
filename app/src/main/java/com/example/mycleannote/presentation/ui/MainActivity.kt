package com.example.mycleannote.presentation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.mycleannote.R
import com.example.mycleannote.presentation.adapter.ShopItemAdapter
import com.example.mycleannote.presentation.adapter.ShopItemAdapter.Companion.VIEW_ITEM_DISABLED
import com.example.mycleannote.presentation.adapter.ShopItemAdapter.Companion.VIEW_ITEM_ENABLED
import com.example.mycleannote.presentation.viewmodel.main.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var shopAdapter: ShopItemAdapter
    private lateinit var floatingActionButton: FloatingActionButton
    private var fragmentContainerView: FragmentContainerView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentContainerView = findViewById(R.id.main_fragment_container_view)
        recyclerView = findViewById(R.id.rv_shop_list)
        floatingActionButton = findViewById(R.id.button_add_shop_item)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        shopAdapter = ShopItemAdapter()

        viewModel.shopList.observe(this) {
            shopAdapter.submitList(it)
        }

        floatingActionButton.setOnClickListener {

            if (fragmentContainerView == null) {
                val intent = ShopItemActivity.newIntentAdd(this)
                startActivity(intent)
            }
            else{
                val fragment = ShopItemFragment.newFragmentAdd()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container_view, fragment).commit()
            }
        }


        with(recyclerView) {
            adapter = shopAdapter
            recycledViewPool.setMaxRecycledViews(VIEW_ITEM_ENABLED, 30)
            recycledViewPool.setMaxRecycledViews(VIEW_ITEM_DISABLED, 30)
        }

        onShopItemClickListener(shopAdapter)
        onShopItemCallBack(recyclerView)


    }

    private fun onShopItemCallBack(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun onShopItemClickListener(shopAdapter: ShopItemAdapter) {
        with(shopAdapter)
        {
            onItemClickListener = {
                if (fragmentContainerView == null) {
                    val intent = ShopItemActivity.newIntentEdit(this@MainActivity, it.id)
                    startActivity(intent)
                }
                else{
                    val fragment = ShopItemFragment.newFragmentEdit(it.id)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container_view, fragment).commit()
                }
            }

            onItemLongClickListener = {
                val item = it.copy(isEnabled = !it.isEnabled)
                viewModel.editShopItem(item)
            }
        }
    }
}