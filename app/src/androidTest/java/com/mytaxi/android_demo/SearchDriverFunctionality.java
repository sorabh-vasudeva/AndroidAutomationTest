package com.mytaxi.android_demo;

import android.Manifest;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Filename  : SearchDriverFunctionality.java
 * Created By: Sorabh Vasudeva
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchDriverFunctionality {





    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule
            = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setup(){
        // Login into application

        String validUserName= "crazydog335";
        String validPassword= "venture";


        onView(withId(R.id.edt_username)).perform(typeText(validUserName));
        onView(withId(R.id.edt_password)).perform(typeText(validPassword));
        onView(withId(R.id.btn_login)).perform(click());
        onView(isRoot()).perform(SearchDriverFunctionality.waitFor(5000));
        onView(withId(R.id.textSearch)).check(matches(withHint(containsString("Search driver here"))));
    }



    /**
     * Test scenario to verify search for valid driver in search driver page.
     */
    @Test
    public void test_searchDriver() {


        String validDriverName = "Sarah Scott";


        onView(withId(R.id.textSearch)).perform(click()).perform(typeText("sa"), closeSoftKeyboard());

        onView(withText(validDriverName))
              .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
               .perform(click());

        // to make a call for searched driver
        onView(withId(R.id.textViewDriverName)).check(matches(allOf(withText(validDriverName))));
        onView(withId(R.id.imageViewDriverAvatar)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewDriverLocation)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewDriverDate)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).perform(click());
    }

    /**
     * action for waiting for specific time
     */
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }


}
