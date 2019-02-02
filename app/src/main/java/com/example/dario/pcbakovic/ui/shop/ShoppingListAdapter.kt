package com.example.dario.pcbakovic.ui.shop

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.database.entity.Item


class ShoppingListAdapter(private var items: ArrayList<Item>) :
    RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder>() {

    private lateinit var mRecentlyDeletedItem: Item
    private var mRecentlyDeletedItemPosition: Int? = null

    inner class MyViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var itemName: TextView = itemView.findViewById(R.id.item_name)
        internal var itemFirstLetter: TextView = itemView.findViewById(R.id.item_first_letter)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (items[position].isDone) {
            holder.itemView.alpha = 0.4f
            holder.itemName.paintFlags = holder.itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemView.alpha = 1.0f
            holder.itemName.paintFlags = holder.itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }


        holder.itemName.text = items[position].name
        holder.itemFirstLetter.text = items[position].name.first().toString()
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    fun updateItems(items: ArrayList<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun doneItem(position: Int) {
        items[position].isDone = !items[position].isDone

        mRecentlyDeletedItem = items[position]
        mRecentlyDeletedItemPosition = position
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()

        items.add(mRecentlyDeletedItemPosition!!, mRecentlyDeletedItem)
    }

    fun getItems() = items
}