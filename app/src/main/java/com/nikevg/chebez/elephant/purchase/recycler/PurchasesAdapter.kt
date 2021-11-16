package com.nikevg.chebez.elephant.purchase.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.elephants.recycler.ElephantsDiffCallback

class PurchasesAdapter(
    private val onPurchaseCallback: (elephant: Elephant) -> Unit
) : ListAdapter<Elephant, PurchasesViewHolder>(ElephantsDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PurchasesViewHolder = PurchasesViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: PurchasesViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), onPurchaseCallback)
    }

    fun removeItem(item: Elephant) {
        val newItems = currentList.toMutableList()
        newItems.remove(item)
        submitList(newItems)
    }
}
