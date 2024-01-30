package com.example.mycleannote.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mycleannote.R
import com.example.mycleannote.presentation.viewmodel.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        var isEnabled = true

        viewModel.shopList.observe(this){
            Log.d("TAG", "onCreate: ${it}")
            if (isEnabled)
            {
                val item = it[1].copy(isEnabled = !it[1].isEnabled)
                viewModel.editShopItem(item)
                isEnabled = false
            }
        }


    }
}