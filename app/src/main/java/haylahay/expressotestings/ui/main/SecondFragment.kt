package haylahay.expressotestings.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import haylahay.expressotestings.R
import kotlinx.android.synthetic.main.second_fragment.*

class SecondFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerForContextMenu(openFloatingContextMenuBtn)

        // Called when the user long-clicks on someView
        openActionModeContextMenuBtn.setOnLongClickListener {
            when (actionMode) {
                null -> {
                    // Start the CAB using the ActionMode.Callback defined above
                    actionMode = activity?.startActionMode(actionModeCallback)
                    view.isSelected = true
                    true
                }
                else -> false
            }
        }

        showHideBtn.setOnClickListener {
            if (showHideResultTV.visibility == View.VISIBLE) {
                showHideResultTV.visibility = View.GONE
            } else {
                showHideResultTV.visibility = View.VISIBLE
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.firstMItem,
            R.id.secondMItem,
            R.id.thirdMItem,
            -> {
                menuResultTV.text = item.title
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.firstCItem,
            R.id.secondCItem,
            R.id.thirdCItem,
            -> {
                menuResultTV.text = item.title
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.firstCItem,
                R.id.secondCItem,
                R.id.thirdCItem,
                -> {
                    menuResultTV.text = item.title
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        tasksLV.adapter = TasksAdapter(requireActivity())

    }

    enum class TaskStatus {
        NotStarted,
        InProgress,
        Completed,
    }

    data class Task(
        val name: String,
        var status: TaskStatus = TaskStatus.NotStarted,
        var lengthInMinutes: Int
    ) {
        override fun equals(other: Any?): Boolean {
            return if (other is Task) {
                other.name == this.name
            } else false
        }
    }

    class TasksAdapter(val activity: Activity) : BaseAdapter() {

        val tasks = arrayOf(
            Task("Task1", lengthInMinutes = 30),
            Task("Task2", lengthInMinutes = 30),
            Task("Task3", lengthInMinutes = 30),
        )

        override fun getCount(): Int = tasks.size

        override fun getItem(position: Int): Any = tasks[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val itemView = (convertView as? TextView) ?: {
                activity.layoutInflater.inflate(R.layout.task_row, parent, false)
            }.invoke()

            itemView.findViewById<TextView>(R.id.taskNameTV).text = tasks[position].name
            itemView.findViewById<TextView>(R.id.taskLengthTV).text =
                "${tasks[position].lengthInMinutes}"
            itemView.findViewById<Spinner>(R.id.taskStatusSpn).adapter = ArrayAdapter<TaskStatus>(
                activity,
                R.layout.task_status,
                arrayOf(TaskStatus.NotStarted, TaskStatus.InProgress, TaskStatus.Completed)
            )

            return itemView
        }

    }

}