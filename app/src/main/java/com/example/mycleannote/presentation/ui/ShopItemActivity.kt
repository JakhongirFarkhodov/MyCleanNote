package com.example.mycleannote.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycleannote.R
import com.example.mycleannote.domain.model.main.ShopItem.Companion.UNDEFINED_ID

class ShopItemActivity : AppCompatActivity() {

    private var screen_mode = EXTRA_SCREEN_MODE_VALUE
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        val fragment = when(screen_mode)
        {
            MODE_ADD -> {
                ShopItemFragment.newFragmentAdd()
            }
            MODE_EDIT ->{
                ShopItemFragment.newFragmentEdit(shopItemId)
            }
            else ->{
                throw RuntimeException("Undefined fragment")
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit()


    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_STRING_VALUE))
        {
            throw RuntimeException("Cannot find extra key:${EXTRA_STRING_VALUE}")
        }

        val mode = intent.getStringExtra(EXTRA_STRING_VALUE)

        if (mode != MODE_ADD && mode != MODE_EDIT)
        {
            throw RuntimeException("Undefined screen mode ${mode}.")
        }

        screen_mode = mode

        if (screen_mode == MODE_EDIT)
        {
            if (!intent.hasExtra(EXTRA_STRING_SHOP_ITEM_ID))
            {
                throw RuntimeException("Screen mode edit cannot find shop item id")
            }
            shopItemId = intent.getIntExtra(EXTRA_STRING_SHOP_ITEM_ID, -1)
        }
    }


    companion object{

        private const val EXTRA_STRING_VALUE = "EXTRA_VALUE"
        private const val EXTRA_STRING_SHOP_ITEM_ID = "ID"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SCREEN_MODE_VALUE = ""

        fun newIntentAdd(context: Context):Intent
        {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_STRING_VALUE, MODE_ADD)
            }
        }

        fun newIntentEdit(context: Context, shopItemId:Int):Intent
        {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_STRING_VALUE, MODE_EDIT)
                putExtra(EXTRA_STRING_SHOP_ITEM_ID, shopItemId)
            }
        }

    }
}