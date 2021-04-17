package haylahay.expressotestings

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.base.DefaultFailureHandler
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheck
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils
import haylahay.expressotestings.ui.main.SecondFragment.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*

@RunWith(AndroidJUnit4::class)
class SecondFragmentInstrumentedTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(SecondActivity::class.java)

    /*
    @Before
    fun before(){
        launchFragmentInContainer<SecondFragment>()
    }
*/

    @Test
    fun test_launchFragmentInContainer() {
        onView(withId(R.id.message))
            .check(matches(withText("SecondFragment")))
    }

    @Test
    fun test_2_1_hasSibling() {
        try {
            onView(withText("30")).check(matches(hasTextColor(R.color.teal_700)))
            throw Exception("Should not have reach here")
        } catch (ex: AmbiguousViewMatcherException) {
        }
        onView(allOf(withText("30"), hasSibling(withText("Task1")))).check(matches(hasTextColor(R.color.teal_700)))
    }

    @Test
    fun test_2_2_matching_view_inside_action_bar() {

        onView(withId(R.id.firstMItem)).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("First")))

        openActionBarOverflowOrOptionsMenu(
            ApplicationProvider.getApplicationContext()
        )

        try {
            onView(withId(R.id.secondMItem)).perform(click())
            throw Exception("Should not have reach here")
        } catch (noMVEx: NoMatchingViewException) {
            assertThat(
                noMVEx.message,
                containsString("No views in hierarchy found matching: with id is <haylahay.expressotestings:id/secondMItem>")
            )
        }

        onView(withText("Second")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Second")))

        openActionBarOverflowOrOptionsMenu(
            ApplicationProvider.getApplicationContext())

        onView(withText("Third")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Third")))
    }

    @Test
    fun test_2_3_matching_view_inside_floating_contextual_action_bar() {

        onView(withId(R.id.openFloatingContextMenuBtn)).perform(longClick())

        onView(withText("Context1")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Context1")))

        onView(withId(R.id.openFloatingContextMenuBtn)).perform(longClick())

        onView(withText("Context3")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Context3")))

        onView(withId(R.id.openFloatingContextMenuBtn)).perform(longClick())

        try {
            onView(withId(R.id.secondCItem)).perform(click())
            throw Exception("Should not have reach here")
        } catch (noMVEx: NoMatchingViewException) {
            assertThat(
                noMVEx.message,
                containsString("No views in hierarchy found matching: with id is <haylahay.expressotestings:id/secondCItem>")
            )
        }

    }

    @Test
    fun test_2_3_matching_view_inside_action_mode_contextual_action_bar() {

        onView(withId(R.id.openActionModeContextMenuBtn)).perform(longClick())

        onView(withText("Context1")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Context1")))

        try {
            // Expect to fail without 'openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext<Context>())'
            onView(withText("Context3")).perform(click())
            throw Exception("Should not have reach here")
        } catch (noMVEx: NoMatchingViewException) {
            assertThat(
                noMVEx.message,
                containsString("No views in hierarchy found matching: with text: is \"Context3")
            )
        }

        onView(withId(R.id.openActionModeContextMenuBtn)).perform(longClick())
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext<Context>())

        onView(withText("Context3")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Context3")))

        onView(withId(R.id.openActionModeContextMenuBtn)).perform(longClick())

        onView(withId(R.id.secondCItem)).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Context2")))

    }

    @Test
    fun test_2_4_assert_a_view_is_displayed_and_not_displayed() {

        onView(withId(R.id.showHideResultTV)).check(matches(isDisplayed()))

        onView(withId(R.id.showHideBtn)).perform(click())
        onView(withId(R.id.showHideResultTV)).check(matches(not(isDisplayed())))

        onView(withId(R.id.showHideBtn)).perform(click())
        onView(withId(R.id.showHideResultTV)).check(matches(isDisplayed()))

    }

    @Test
    fun test_2_5_assert_view_is_present_and_not() {

        onView(withId(R.id.showHideBtn)).check(matches(not(doesNotExist())))

        // monthsRV is declared in another layout
        onView(withId(R.id.monthsRV)).check(doesNotExist())

    }

    private fun<T> viewWithAdaptedData(task: T): Matcher<View> {
        return object : BaseMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("viewWithAdaptedData ")
                description?.appendText(task.toString())
            }

            override fun describeMismatch(item: Any?, mismatchDescription: Description?) {
                mismatchDescription?.appendText("TODO: $item")
            }

            override fun matches(view: Any): Boolean {
                if (view !is AdapterView<*>) {
                    return false
                }

                val adapter = view.adapter
                for (i in 0 until adapter.count) {
                    if (task == adapter.getItem(i)) {
                        return true
                    }
                }

                return false
            }

        }
    }

    @Test
    fun test_2_6_assert_data_item_is_not_in_an_adapter() {

        val task1 = Task("Task1", lengthInMinutes = 30)
        val task4 = Task("Task4", lengthInMinutes = 30)

        onView(withId(R.id.tasksLV))
            .check(matches(viewWithAdaptedData(task1)))

        onView(withId(R.id.tasksLV))
            .check(matches(not(viewWithAdaptedData(task4))))

    }

    @Test
    fun test_2_7_creating_and_handling_custom_failure() {

        class EspressoTestingsException(message: String) : Exception(message)

        class CustomFailureHandler(targetContext: Context) : FailureHandler {
            private val delegate: FailureHandler

            init {
                delegate = DefaultFailureHandler(targetContext)
            }
            /**
             * Handle the given error in a manner that makes sense to the environment in which the test is
             * executed (e.g. take a screenshot, output extra debug info, etc). Upon handling, most handlers
             * will choose to propagate the error.
             */
            override fun handle(error: Throwable?, viewMatcher: Matcher<View>?) {
                throw EspressoTestingsException("***$error*** and ***$viewMatcher***")
                // TODO: find out how to take screenshot.
                //delegate.handle(error, viewMatcher)
            }

        }

        setFailureHandler(CustomFailureHandler(ApplicationProvider.getApplicationContext()))

        val task1 = Task("Task1", lengthInMinutes = 30)

        try {
            onView(withId(R.id.tasksLV)).check(matches(not(viewWithAdaptedData(task1))))
        } catch (etex: EspressoTestingsException) {
            // Do nothing here as all error should become this exception
        }

        setFailureHandler(DefaultFailureHandler(ApplicationProvider.getApplicationContext()))

    }

    @Test
    fun test_3_1_enable_accessibility_check() {

        AccessibilityChecks.enable().apply {
            setSuppressingResultMatcher(
                anyOf(
                    AccessibilityCheckResultUtils.matchesViews(withId(-1)),
                    AccessibilityCheckResultUtils.matchesViews(withId(R.id.taskStatusSpn)),
                    AccessibilityCheckResultUtils.matchesViews(withId(R.id.openFloatingContextMenuBtn)),
                )
            )
            setRunChecksFromRootView(true)
        }
        onView(withId(R.id.openActionModeContextMenuBtn)).perform(click())
    }

}