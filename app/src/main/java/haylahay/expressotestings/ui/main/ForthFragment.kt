package haylahay.expressotestings.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import haylahay.expressotestings.R
import kotlinx.android.synthetic.main.forth_fragment.*

class ForthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.forth_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksLV.adapter = SecondFragment.TasksAdapter(requireActivity())

/*
        val footerTV = TextView(requireActivity())
        footerTV.text = "Footer"
        tasksLV.addFooterView(footerTV)

        val headerTV = TextView(requireActivity())
        headerTV.text = "Header"
        tasksLV.addHeaderView(headerTV)
*/

        monthsRV.layoutManager = LinearLayoutManager(activity)
        monthsRV.adapter = MonthsRVAdapter(this::onMonthClicked)

    }

    private fun onMonthClicked(month: Int) {
        System.out.println("Displaying $month")
        inputET.setText("$month")
    }

}