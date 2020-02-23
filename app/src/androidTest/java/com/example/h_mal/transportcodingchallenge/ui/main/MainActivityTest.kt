package com.example.h_mal.transportcodingchallenge.ui.main


import android.net.wifi.WifiManager
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.h_mal.transportcodingchallenge.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    val submitButton = onView(allOf(withId(R.id.submit), withText("Submit"), isDisplayed()))
    val searchEditText = onView(allOf(withId(R.id.search_box),  isDisplayed()))

    @Test
    fun testSubmit_emptySearch() {
        submitButton.perform(click())

        testToast("No Road ID inserted")
    }

    @Test
    fun testSubmit_SearchInvalidEntry() {

        searchEditText.perform(replaceText("A31222"), closeSoftKeyboard())
        submitButton.perform(click())
        waitFor(1000)
        testToast(
            "The following road id is not recognised: A31222"
        )
    }

    @Test
    fun testSubmit_SearchValidEntry() {

        searchEditText.perform(replaceText("A40"), closeSoftKeyboard())
        submitButton.perform(click())
        waitFor(1000)

        onView(allOf(
                withId(R.id.road_id),
                withText("A40"),
                isDisplayed()
            )
        )

        onView(
            allOf(
                withText("Road Status"),
                isDisplayed()
            )
        )

        onView(
            allOf(
                withText("Road Description"),
                isDisplayed()
            )
        )

    }

    private fun testToast(toastText:String){
        onView(withText(toastText))
            .inRoot(withDecorView(not(`is`(mActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))

        waitFor(2000)
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
