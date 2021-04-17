package haylahay.expressotestings

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import haylahay.expressotestings.ui.main.SecondFragment
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActionBarTestActivityTest : TestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(ActionBarTestActivity::class.java)
//    val mRule = ActivityTestRule(ActionBarTestActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.firstMItem)).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("First")))

        // TODO: Test on actual device

//        openContextualActionModeOverflowMenu()
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext())
//        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())

//        openActionBarOverflowOrOptionsMenu(mRule.activity);

        // withId failed, yet withText works
        onView(withId(R.id.secondMItem)).perform(click())
        //onView(withText("")).perform(click())
        onView(withId(R.id.menuResultTV)).check(matches(withText("Second")))

    }
}