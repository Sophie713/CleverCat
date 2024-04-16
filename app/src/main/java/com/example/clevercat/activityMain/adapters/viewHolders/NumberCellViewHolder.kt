package com.example.clevercat.activityMain.adapters.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.clevercat.databinding.ItemNumberLayoutBinding

class NumberCellViewHolder(binding: ItemNumberLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
   val numberTextView = binding.numberLayoutNumber
// ** DEBUG **
//    val top = binding.numberLayoutTop
//    val bottom = binding.numberLayoutBottom
//    val start = binding.numberLayoutStart
//    val end = binding.numbersLayoutEnd
// ** DEBUG **
    val numberLayout = binding.numberLayout
}