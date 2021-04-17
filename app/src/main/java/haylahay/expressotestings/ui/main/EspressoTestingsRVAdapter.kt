package haylahay.expressotestings.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EspressoTestingsRVAdapter(val texts: List<String>) : RecyclerView.Adapter<EspressoTestingsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EspressoTestingsVH =
        EspressoTestingsVH(TextView(parent.context))

    override fun onBindViewHolder(holder: EspressoTestingsVH, position: Int) {
        (holder.aView as TextView).text = texts[position]
    }

    override fun getItemCount(): Int = texts.size

}

class EspressoTestingsVH(val aView: View) : RecyclerView.ViewHolder(aView)