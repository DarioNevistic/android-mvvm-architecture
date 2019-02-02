package com.example.dario.pcbakovic.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.model.ItemModel


class CustomHolder(val context: Context, customView: View) : RecyclerView.ViewHolder(customView) {

    fun bind(item: ItemModel) {
        with(item) {
            val image = itemView.findViewById<ImageView>(R.id.action_item_image)
            val title = itemView.findViewById<TextView>(R.id.action_item_title)


            title.text = item.title

            Glide
                .with(context)
                .asBitmap()
                .load(item.url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .fitCenter()
                )
                .into(image)
        }
    }
}