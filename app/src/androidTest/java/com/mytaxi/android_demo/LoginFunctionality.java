package com.mytaxi.android_demo;

import android.Manifest;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import com.mytaxi.android_demo.activities.MainActivity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;


import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.containsString;

import static org.hamcrest.Matchers.not;

/**
 * Filename  : LoginFunctionality.java
 * Created By: Sorabh Vasudeva
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


//declaring a class which has all the tests related to Login functionality

public class LoginFunctionality {

    private String validUserName = "crazydog335";
    private String validPassword = "venture";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule
            = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);


    /**
     * Test scenario to verify case of blank username & blank password
     */

    @Test
    public void testCase1_blank_UserName_Password() {
        onView(withId(R.id.edt_username)).perform(clearText());
        onView(withId(R.id.edt_password)).perform(clearText());
        onView(withId(R.id.btn_login)).perform(click());
        onView(isRoot()).perform(SearchDriverFunctionality.waitFor(1000));

        //Verify error message for blank username and password is displayed
        onView(withText(R.string.blank_username_password))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }


    /**
     * Test scenario to verify case of blank username
     */

    @Test
    public void testCase2_blank_UserName() {
        onView(withId(R.id.edt_username)).perform(clearText());
        onView(withId(R.id.edt_password)).perform(typeText(validPassword));
        onView(withId(R.id.btn_login)).perform(click());
        onView(isRoot()).perform(SearchDriverFunctionality.waitFor(1000));

        //Verify error message for blank username is displayed
        onView(withText(R.string.blank_username))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * Test scenario to verify case of blank password
     */
    @Test
    public void testCase3_blank_Password() {
        onView(withId(R.id.edt_username)).perform(typeText(validUserName));
        onView(withId(R.id.edt_password)).perform(clearText());
        onView(withId(R.id.btn_login)).perform(click());
        onView(isRoot()).perform(SearchDriverFunctionality.waitFor(1000));

        //Verify error message for blank Password is displayed
        onView(withText(R.string.blank_password))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * Test scenario to verify case of invalid username and invalid  password
     */

    @Test
    public void testCase4_invalid_UserName_Password() {
        onView(withId(R.id.edt_username)).perform(typeText("invalidUserName"));
        onView(withId(R.id.edt_password)).perform(typeText("invalidPassword"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(isRoot()).perform(SearchDriverFunctionality.waitFor(1000));

        //Verify error message for Invalid username and password is displayed
        onView(withText(R.string.message_login_fail))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }


    /**
     * Test to verify valid username and valid password for successful login.
     */

    @Test
    public void testCase5_successfulLogin() {
        try {
            onView(withId(R.id.edt_username)).perform(typeText(validUserName));
            onView(withId(R.id.edt_password)).perform(typeText(validPassword));
            onView(withId(R.id.btn_login)).perform(click());
            onView(isRoot()).perform(SearchDriverFunctionality.waitFor(5000));

            //Verify it has navigated to next screen
            onView(withId(R.id.textSearch)).check(matches(withHint(containsString("Search driver here"))));


            // Logout operation
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            onView(withText("Logout")).perform(click());
            onView(withId(R.id.edt_username)).check(matches(isDisplayed()));

        } catch (Exception e) {
            fail("Login was not successful" + e.getMessage());
        }


    }
}
