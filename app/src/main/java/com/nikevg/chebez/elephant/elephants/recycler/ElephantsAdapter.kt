package com.nikevg.chebez.elephant.elephants.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nikevg.chebez.elephant.database.Elephant

class ElephantsAdapter : ListAdapter<Elephant, ElephantsViewHolder>(ElephantsDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElephantsViewHolder = ElephantsViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ElephantsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}
