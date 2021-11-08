package com.nikevg.chebez.elephant.elephants.recycler

import androidx.recyclerview.widget.DiffUtil
import com.nikevg.chebez.elephant.database.Elephant

class ElephantsDiffCallback : DiffUtil.ItemCallback<Elephant>() {

    override fun areItemsTheSame(
        oldItem: Elephant,
        newItem: Elephant
    ): Boolean = oldItem.nameRes == newItem.nameRes

    override fun areContentsTheSame(
        oldItem: Elephant,
        newItem: Elephant
    ): Boolean = oldItem == newItem
}
