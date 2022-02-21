package com.example.salon.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.salon.R
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("imageUrl")
fun loadImage(view: SimpleDraweeView, url: String) {
    view.setImageURI(url)
}

@BindingAdapter("text")
fun setPrice(view: TextView, price: Int) {
    view.text = String.format(view.context.getString(R.string.price), price.toDouble())
}