package com.nikevg.chebez.elephant.purchase.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikevg.chebez.elephant.R
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.databinding.ItemPurchaseBinding

class PurchasesViewHolder(private val binding: ItemPurchaseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(elephant: Elephant, onClick: (elephant: Elephant) -> Unit) {
        with(binding) {
            elephantName.text = itemView.context.getString(elephant.nameRes)
            elephantId.text =
                itemView.context.getString(R.string.elephant_id_pattern, elephant.id)
            elephantImage.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    elephant.drawableRes
                )
            )
            buyButton.setOnClickListener {
                onClick(elephant)
            }
        }
    }

    companion object {

        fun from(
            parent: ViewGroup
        ): PurchasesViewHolder = PurchasesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_purchase,
                parent,
                false
            )
        )
    }
}
