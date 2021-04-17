package haylahay.expressotestings

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import haylahay.expressotestings.ui.main.EspressoTestingsVH
import haylahay.expressotestings.ui.main.ForthFragment
import haylahay.expressotestings.ui.main.SecondFragment.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForthFragmentInstrumentedTest {

    @Before
    fun before(){
        launchFragmentInContainer<ForthFragment>()
    }

    private fun isTaskRowDisplayingTask(task: Task): Matcher<View> {
        return object : BaseMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText(task.toString())
            }

            override fun matches(item: Any?): Boolean {
                System.out.println("Comparing: $task vs $item")
                return if (item is TextView) {
                    item.text == task.name
                } else if (item is ConstraintLayout) {
                    val textView = item.children.firstOrNull {
                        it.id == R.id.taskNameTV
                    } as TextView
                    textView.text == task.name
                } else
                    false
            }

        }
    }

    private fun withTask(task: Task): Matcher<Task> {
        return object : BaseMatcher<Task>() {
            override fun describeTo(description: Description?) {
                description?.appendText(task.toString())
            }

            override fun matches(item: Any?): Boolean {
                System.out.println("Comparing: $task vs $item")
                return if (item is Task) {
                    item == task
                } else
                    false
            }

        }
    }

    @Test
    fun test_4_1_Match_data_using_a_custom_view_matcher() {
        val task = Task("Task1", TaskStatus.Completed, 30)
        //val task2 = Task("Task2", TaskStatus.Completed, 30)
        onData(`is`(task))
            .inAdapterView(withId(R.id.tasksLV))
            .check(matches(isTaskRowDisplayingTask(task)))
        // Would cause error and test to fail
        //.check(matches(isTaskRowDisplayingTask(task2)))
        // Equivalent
        onData(`is`(task))
            .inAdapterView(withId(R.id.tasksLV))
            .check(matches(withChild(withText(task.name))))
    }

    @Test
    fun test_4_1_Match_data_using_a_custom_data_matcher() {
        val task = Task("Task1", TaskStatus.Completed, 30)
        onData(withTask(task))
            .inAdapterView(withId(R.id.tasksLV))
            .check(matches(isTaskRowDisplayingTask(task)))
        // Equivalent
        onData(`is`(task))
            .inAdapterView(withId(R.id.tasksLV))
            .check(matches(withChild(withText(task.name))))
    }

    @Test
    fun test_4_2_match_data_to_a_specific_view() {

        val task = Task("Task1", TaskStatus.Completed, 30)
        onData(`is`(task))
            .inAdapterView(withId(R.id.tasksLV))
            .onChildView(withText("Task1")).check(matches(withId(R.id.taskNameTV)))
        onData(withTask(task))
            .inAdapterView(withId(R.id.tasksLV))
            .onChildView(withText("Task1")).check(matches(withId(R.id.taskNameTV)))
    }

    @Test
    fun test_4_4_use_scrollTo() {

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollTo<EspressoTestingsVH>(withText("Dec")),
            )
        onView(withText("Dec")).perform(click())
        onView(withId(R.id.inputET))
            .check(matches(withText("12")))

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollTo<EspressoTestingsVH>(withText("Jan")),
            )
        onView(withText("Jan")).perform(click())
        onView(withId(R.id.inputET))
            .check(matches(withText("1")))

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollTo<EspressoTestingsVH>(withText("Smarch")),
            )
        onView(withText("Smarch")).perform(click())
        onView(withId(R.id.inputET))
            .check(matches(withText("13")))

/*
        // Should fail after scrolling to "Smarch"
        onView(withText("Jan")).perform(click())
        onView(withId(R.id.inputET))
            .check(matches(withText("1")))
*/

/*
        // Follow is to failed
        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollTo<EspressoTestingsVH>(withText("Tapril"))
            )
*/
    }

    fun <E: EspressoTestingsVH> matchAViewHolder( itemId: Long): BaseMatcher<E>{
        return object : BaseMatcher<E>() {
            override fun describeTo(description: Description?) {
                description?.appendText("Attempting to match VH with itemId $itemId")
            }

            override fun matches(item: Any?): Boolean {
                System.out.println("Item is $item")
                return if (item is EspressoTestingsVH) {
                    itemId == item.itemId
                } else false
            }

        }
    }

    // Requires a way to id ViewHolder of which is not what RecyclerView intended. All ViewHolders are the same so no need to scroll to specific VH. This exercise will be intentionally left uncompleted.
    @Test
    fun test_4_5_use_scrollToHolder() {

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToHolder(matchAViewHolder(1L)),
                click()
            )
        onView(withId(R.id.inputET))
            .check(matches(withText("2")))
    }

    @Test
    fun test_4_6_use_scrollToPosition() {

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToPosition<EspressoTestingsVH>(10),
                click(),
            )

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToPosition<EspressoTestingsVH>(0),
                click(),
            )

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToPosition<EspressoTestingsVH>(11),
                click(),
            )

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToPosition<EspressoTestingsVH>(130),
                click(),
            )

        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.scrollToPosition<EspressoTestingsVH>(-130),
                click(),
            )

    }

    @Test
    fun test_4_7_use_actionOnHolderItem() {
        onView(withId(R.id.monthsRV))
            .perform(
                RecyclerViewActions.actionOnHolderItem(matchAViewHolder(1L), click()),
                RecyclerViewActions.actionOnItemAtPosition<EspressoTestingsVH>(1, click())
            )
    }
}
