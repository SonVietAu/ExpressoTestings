package haylahay.expressotestings.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import haylahay.expressotestings.R
import java.util.*

class MonthsRVAdapter(val onClick: (month: Int) -> Unit) : RecyclerView.Adapter<EspressoTestingsVH>() {

    private val months = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "Smarch"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EspressoTestingsVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.month_vh, parent, false) as Button
        return EspressoTestingsVH(itemView)
    }

    override fun onBindViewHolder(holder: EspressoTestingsVH, position: Int) {
        (holder.aView as Button).text = months[position]
        (holder.aView as Button).setOnClickListener {
            onClick(position + 1)
        }
    }

    override fun getItemCount(): Int = months.size

}