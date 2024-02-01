package com.example.mycleannote.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycleannote.R
import com.example.mycleannote.domain.model.main.ShopItem
import com.example.mycleannote.presentation.viewmodel.shopItem.ShopItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var tieName: TextInputEditText
    private lateinit var tieCount: TextInputEditText
    private lateinit var save_button: MaterialButton

    private lateinit var viewModel: ShopItemViewModel
    private var screen_mode = SCREEN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initValue(view)

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
        viewModel.shopItem.observe(viewLifecycleOwner){
            val name = it.name
            val count = it.count.toString()
            tieName.setText(name)
            tieCount.setText(count)
        }

        viewModel.errorInputName.observe(viewLifecycleOwner){
            if (it)
            {
                tilName.error = getString(R.string.error_input_name)
            }
            else{
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner){
            if (it)
            {
                tilCount.error = getString(R.string.error_input_count)
            }
            else{
                tilCount.error = null
            }
        }

        viewModel.shouldCloseActivity.observe(viewLifecycleOwner){
            requireActivity().finish()
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

        tieName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.reverseInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        tieCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.reverseInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }


    private fun initValue(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        tieName = view.findViewById(R.id.et_name)
        tieCount = view.findViewById(R.id.et_count)
        save_button = view.findViewById(R.id.save_button)
    }

    private fun parseArguments() {

        if (!requireArguments().containsKey(STRING_MODE))
        {
            throw RuntimeException("Arguments cannot find string mode:${SCREEN_MODE}.")
        }

        val mode = requireArguments().getString(STRING_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT)
        {
            throw RuntimeException("Undefined screen mode: ${mode}")
        }
        screen_mode = mode

        if (screen_mode == MODE_EDIT)
        {
            if (!requireArguments().containsKey(STRING_SHOP_ITEM_ID))
            {
                throw RuntimeException("Screen mode edit does not exist shop item id")
            }
            shopItemId = requireArguments().getInt(STRING_SHOP_ITEM_ID, -1)
        }

    }


    companion object{

        private const val STRING_MODE = "STRING_MODE"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val STRING_SHOP_ITEM_ID = "id"
        private const val SCREEN_MODE = ""


        fun newFragmentAdd() : ShopItemFragment
        {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STRING_MODE, MODE_ADD)
                }
            }
        }

        fun newFragmentEdit(shopItemId:Int) : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(STRING_MODE, MODE_EDIT)
                    putInt(STRING_SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }

}