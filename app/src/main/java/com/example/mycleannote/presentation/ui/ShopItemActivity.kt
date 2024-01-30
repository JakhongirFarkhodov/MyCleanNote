package com.example.mycleannote.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mycleannote.R
import com.example.mycleannote.domain.model.main.ShopItem
import com.example.mycleannote.domain.model.main.ShopItem.Companion.UNDEFINED_ID
import com.example.mycleannote.presentation.viewmodel.shopItem.ShopItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName:TextInputLayout
    private lateinit var tilCount:TextInputLayout
    private lateinit var tieName:TextInputEditText
    private lateinit var tieCount:TextInputEditText
    private lateinit var save_button:MaterialButton

    private lateinit var viewModel: ShopItemViewModel
    private var screen_mode = EXTRA_SCREEN_MODE_VALUE
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseExtras()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initValue()

        when(screen_mode)
        {
            MODE_ADD ->{
                launchAddShopItem()
            }
            MODE_EDIT ->{
                launchEditShopItem()
            }
        }

        subscribeObservers()

        save_button.setOnClickListener {

            val name = tieName.text.toString()
            val count = tieCount.text.toString()

            when(screen_mode)
            {
                MODE_ADD ->{
                    viewModel.addShopItem(name, count)
                }
                MODE_EDIT ->{
                    viewModel.editShopItem(name,count)
                }
            }
        }

    }

    private fun subscribeObservers() {
        viewModel.shopItem.observe(this){
            val name = it.name
            val count = it.count.toString()
            tieName.setText(name)
            tieCount.setText(count)
        }

        viewModel.errorInputName.observe(this){
            if (it)
            {
                tilName.error = getString(R.string.error_input_name)
            }
            else{
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(this){
            if (it)
            {
                tilCount.error = getString(R.string.error_input_count)
            }
            else{
                tilCount.error = null
            }
        }

        viewModel.shouldCloseActivity.observe(this){
            finish()
        }
    }

    private fun launchAddShopItem() {
        onTextChange()
    }


    private fun launchEditShopItem() {
        viewModel.getShopItem(shopItemId)
        onTextChange()
    }


    private fun onTextChange() {

        tieName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.reverseInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        tieCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.reverseInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }


    private fun initValue() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        tieName = findViewById(R.id.et_name)
        tieCount = findViewById(R.id.et_count)
        save_button = findViewById(R.id.save_button)
    }

    private fun parseExtras() {
        if (!intent.hasExtra(EXTRA_STRING_VALUE))
        {
            throw RuntimeException("Cannot find ${EXTRA_STRING_VALUE}")
        }

        val mode = intent.getStringExtra(EXTRA_STRING_VALUE)
        if (mode != MODE_ADD && mode != MODE_EDIT)
        {
            throw RuntimeException("Unknown Mode found ${mode}")
        }

        screen_mode = mode

        if (screen_mode.equals(MODE_EDIT))
        {
            if (!intent.hasExtra(EXTRA_STRING_SHOP_ITEM_ID))
            {
                throw RuntimeException("Cannot find shopItem id")
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