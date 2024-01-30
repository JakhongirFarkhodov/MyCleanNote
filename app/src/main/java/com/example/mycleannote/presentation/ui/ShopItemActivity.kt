package com.example.mycleannote.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mycleannote.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        val data = intent.getStringExtra(EXTRA_STRING_VALUE)
        Log.d("TAG", "onCreate: ${data}")

    }

    companion object{

        private const val EXTRA_STRING_VALUE = "EXTRA_VALUE"
        private const val EXTRA_STRING_SHOP_ITEM_ID = "ID"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentAdd(context: Context):Intent
        {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_STRING_VALUE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId:Int):Intent
        {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_STRING_VALUE, MODE_EDIT)
            intent.putExtra(EXTRA_STRING_SHOP_ITEM_ID, shopItemId)

            return intent
        }

    }
}