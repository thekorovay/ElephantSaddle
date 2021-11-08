package com.nikevg.chebez.elephant.elephants.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.databinding.ItemElephantBinding

class ElephantsViewHolder(
    private val binding: ItemElephantBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(elephant: Elephant) {
        binding.elephant = elephant
    }

    companion object {

        fun from(
            parent: ViewGroup
        ): ElephantsViewHolder = ElephantsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_elephant,
                parent,
                false
            )
        )
    }
}
