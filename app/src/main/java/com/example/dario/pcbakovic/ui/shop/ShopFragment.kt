package com.example.dario.pcbakovic.ui.shop

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.base.BaseFragment
import com.example.dario.pcbakovic.database.entity.Item
import com.example.dario.pcbakovic.di.ViewModelFactory
import com.example.dario.pcbakovic.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_shop.*
import timber.log.Timber
import javax.inject.Inject


class ShopFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ShoppingListAdapter


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ShopViewModel

    private var itemsList: ArrayList<Item> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShopViewModel::class.java)
        viewModel.shoppingItems.observe(this, Observer { items ->
            Timber.d("Items are $items")
            itemsList = arrayListOf()
            itemsList = items!!
            viewAdapter.updateItems(items)
        })

        viewModel.showEmptyListHolder.observe(this, Observer { empty ->
            if (empty!!) showEmptyListHolder()
        })

        add_items_to_list_fab.setOnClickListener { addNewItemToList() }
        share_shopping_list_btn.setOnClickListener {
            if (itemsList.size > 0)
                shareItemsList()
            else
                showToastMessage("Popis za kupovinu je prazan!")
        }

        delete_shopping_list_btn.setOnClickListener {
            if (itemsList.size > 0)
                deleteAllItems()
            else
                showToastMessage("Popis za kupovinu je prazan!")

        }
    }

    private fun deleteAllItems() {
        val builder = AlertDialog.Builder(activity!!)

        builder.setTitle("Potvrdite brisanje")
        builder.setMessage("Jeste li sigurni da želite izbrisati popis?")

        builder.setPositiveButton("Da") { dialog, _ ->
            itemsList = arrayListOf()
            viewAdapter.clear()
            viewModel.deleteAll()
            showEmptyListHolder()
            dialog.dismiss()
        }

        builder.setNegativeButton("Ne") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun shareItemsList() {
        var itemListAsString = "Popis za kupovinu\n"

        itemsList.forEach { it ->
            itemListAsString += "-${it.name}\n"
        }

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, itemListAsString)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun setupRecyclerView() {
        recyclerView = shopping_list_recyclerview

        viewAdapter = ShoppingListAdapter(itemsList)

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = viewAdapter

        val swipeHandler = object : SwipeToDeleteCallback(activity!!.baseContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    viewAdapter.doneItem(viewHolder.adapterPosition)
                    viewModel.storeItemsToDb(viewAdapter.getItems())

                } else if (direction == ItemTouchHelper.LEFT) {
                    val item = itemsList[viewHolder.adapterPosition]

                    viewAdapter.removeAt(viewHolder.adapterPosition)
                    viewModel.deleteItemFromDb(item)

                    if (viewAdapter.getItems().size == 0)
                        showEmptyListHolder()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun hideEmptyListHolder() {
        if (shop_list_empty_textview.visibility == View.VISIBLE)
            shop_list_empty_textview.visibility = View.GONE
    }

    private fun showEmptyListHolder() {
        if (shop_list_empty_textview.visibility == View.GONE)
            shop_list_empty_textview.visibility = View.VISIBLE
    }

    private fun addNewItemToList() {
        val dialogBuilder = AlertDialog.Builder(activity!!).create()
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_item, null)
        dialogBuilder.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        val editText = dialogView.findViewById(R.id.shop_item_edittext) as EditText
        val button1 = dialogView.findViewById(R.id.buttonSubmit) as Button
        val button2 = dialogView.findViewById(R.id.buttonCancel) as Button

        button2.setOnClickListener { dialogBuilder.dismiss() }
        button1.setOnClickListener {
            val item = Item(editText.text.toString())

            if (!itemsList.contains(item)) {
                itemsList.add(item)
                viewModel.storeItemsToDb(itemsList)
                viewAdapter.updateItems(itemsList)
            } else
                showToastMessage("Popis već sadrži uneseni artikal!")

            hideEmptyListHolder()
            dialogBuilder.dismiss()
        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    }

    companion object {
        fun newInstance(): ShopFragment {
            return ShopFragment()
        }
    }
}