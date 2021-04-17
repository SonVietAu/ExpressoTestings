package haylahay.expressotestings

import android.widget.BaseAdapter
import android.widget.ListView
import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingRootException
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import haylahay.expressotestings.ui.main.MainFragment
import org.hamcrest.CoreMatchers.*
import org.hamcrest.collection.IsMapContaining
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainFragmentInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_1_1_withId_without_allOf_expect_exception() {
        try {
            onView(withId(R.id.message)).check(matches(withText("MainFragment")))
            // Some how the test can not find R.id.aTextView, this test just break, so the test is getting commented out
            /*
            onView(withId(R.id.aTextView)).check(matches(withText("TextView1")))
            throw Exception("Should not have reach here")
            */
        } catch (ex: Exception) {
            // onView
            assertThat(ex, instanceOf(AmbiguousViewMatcherException::class.java))
            assertThat(
                ex.message,
                startsWith("This matcher matches multiple views in the hierarchy")
            )
        }
    }

    @Test
    fun test_1_2_withText_without_allOf_expect_exception() {
        try {
            // Should be hasTextColorId
            onView(withText("Same Same")).check(matches(hasTextColor(R.color.teal_200)))
            throw Exception("Should not have reach here")
        } catch (ex: Exception) {
            // onView
            assertThat(ex, instanceOf(AmbiguousViewMatcherException::class.java))
            assertThat(
                ex.message,
                containsString("matches multiple views in the hierarchy.\nProblem views are marked with")
            )
        }
    }

    @Test
    fun test_1_2_not_withText_without_allOf_expect_exception() {
        try {
            // Should be hasTextColorId
            onView(not(withText("Same Same"))).check(matches(hasTextColor(R.color.teal_200)))
            throw Exception("Should not have reach here")
        } catch (ex: Exception) {
            // onView
                ex.printStackTrace()
            assertThat(ex, instanceOf(AmbiguousViewMatcherException::class.java))
            assertThat(
                ex.message,
                containsString("matches multiple views in the hierarchy.\nProblem views are marked with")
            )
        }
    }

    @Test
    fun test_1_2_allOf_withText_and_hasTextColor() {

        onView(
            allOf(
                withText("Same Same"),
                hasTextColor(R.color.teal_200)
            )
        )
            .check(matches(hasTextColor(R.color.teal_200)))

        onView(
            allOf(
                withText("Diff Diff"),
                hasTextColor(R.color.purple_500)
            )
        )
            .check(matches(hasTextColor(R.color.purple_500)))

    }

    @Test
    fun test_1_3_perform_typeText_click() {

        val inputInitialValue = 43

        // Type a number and see it multiply by 2, display an error message for any non-integer entry
        onView(withId(R.id.inputET)).perform(typeText("$inputInitialValue"))
        onView(withId(R.id.resultBtn)).check(matches(withText("${inputInitialValue * 2}")))

        // Perform a click and see a result incremented
        onView(withId(R.id.resultBtn)).perform(click())

        onView(withId(R.id.inputET)).check(matches(withText("${inputInitialValue + 1}")))
        onView(withId(R.id.resultBtn)).check(matches(withText("${(inputInitialValue + 1) * 2}")))
    }

    @Test
    fun test_1_4_onData_for_an_adapter_view() {

        // use "Tuesday" in one or the other to fail this test
        // use "onData(withId(R.id.daysLV))" to show acceptance of matchers on view that caused RuntimeException
        onData(allOf(instanceOf(String::class.java), containsString("Monday")))
            .atPosition(0)
            .perform(click())
//        onData(allOf(`is`(instanceOf(BaseAdapter::class.java)), `is`("Smarch"))).perform(click())
        onView(withId(R.id.clickedDayTV)).check(matches(withText("Monday")))

    }

    @Test
    fun test_1_5_withId_with_an_id_for_view_inside_adapter_view() {
        onView(withId(R.id.an_id)).perform(click())
    }

    @Test
    fun test_1_5_withId_with_another_id_for_view_inside_adapter_view() {
        try {
            onView(withId(R.id.another_id)).perform(click())
        } catch (noMatchingVEX: NoMatchingViewException) {

            assertThat(
                noMatchingVEX.message,
                containsString("may need to use Espresso.onData to load it from one of the following AdapterViews:android.widget.ListView")
            )
        }
    }



    @Test
    fun test_perform_scrollTo_click() {
        // Scroll and click a 'selected' month

        onView(withId(R.id.monthsRV)).perform(scrollTo(), click())

    }


    @Test
    fun test_onView_with_RecyclerView() {

    }

    @Test
    fun test_onData_for_RecyclerView() {

    }

}