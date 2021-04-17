package haylahay.expressotestings.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import haylahay.expressotestings.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var lastNumber = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultBtn.setOnClickListener {
            onResultTVClicked()
        }

        inputET.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    lastNumber = s?.toString()?.toInt() ?: lastNumber
                    resultBtn.text = "${lastNumber * 2}"
                    message.text = ""
                } catch (numbFmtEx: NumberFormatException) {
                    inputET.setError("Input must be a number")
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        daysLV.setOnItemClickListener { parent: AdapterView<*>, itemView, position, id ->
            clickedDayTV.text = parent.adapter.getItem(position) as String
        }

    }

    private fun onResultTVClicked() {
        inputET.setText("${lastNumber + 1}")
    }

    private fun onMonthClicked(month: Int) {
        inputET.setText("$month")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        daysLV.adapter = DaysAdapter(requireActivity())

        monthsRV.layoutManager = LinearLayoutManager(activity)
        monthsRV.adapter = MonthsRVAdapter(this::onMonthClicked)

    }

    private class DaysAdapter(val activity: Activity): BaseAdapter() {

        val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "SmarchDay")

        override fun getCount(): Int = days.size

        override fun getItem(position: Int): Any = days[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val itemView = (convertView as? TextView) ?: {
                val textView = TextView(activity)
                textView.id = R.id.an_id
                textView
            }.invoke()
                itemView.text = days[position]
                return itemView
        }

    }
}