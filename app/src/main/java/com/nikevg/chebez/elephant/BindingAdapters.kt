package com.nikevg.chebez.elephant

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.nikevg.chebez.elephant.database.Elephant
import com.nikevg.chebez.elephant.elephants.recycler.ElephantsAdapter

@BindingAdapter("elephants")
fun RecyclerView.setElephants(elephants: List<Elephant>) {
    if (adapter == null) {
        adapter = ElephantsAdapter()
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    (adapter as? ElephantsAdapter)?.submitList(elephants)
}
