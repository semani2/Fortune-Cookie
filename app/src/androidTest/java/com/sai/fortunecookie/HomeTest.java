package com.sai.fortunecookie;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sai.fortunecookie.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by sai on 1/24/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);


    /**
     * Testing the basic scenario of the app
     */
    @Test
    public void test_NoMessage_Loading() {
        onView(withId(R.id.loading_progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.refresh_fab)).check(matches(not(isDisplayed())));
    }

    /**
     * Testing loading a fortune message, takes a maximum of 10 seconds
     */
    @Test
    public void test_Message_Loading_Successful() {
        onView(withId(R.id.loading_progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.refresh_fab)).check(matches(not(isDisplayed())));

        sleep(10000);

        // Message should load successfully
        onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.fortune_message_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(not(withText(""))));

        onView(withId(R.id.refresh_fab)).check(matches(isDisplayed()));
    }

    /**
     * Testing reloading a message on pressing the FAB
     */
    @Test
    public void test_FAB_Starts_LoadsMessage() {
        // Wait for first load to happen
        sleep(10000);

        onView(withId(R.id.refresh_fab)).perform(click());

        onView(withId(R.id.loading_progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.refresh_fab)).check(matches(not(isDisplayed())));
    }

    /**
     * Testing the case where there is no network connection,
     * loads the default message to the user
     * P.S: Please test this case with mobile data tuned off, wifi can be turned on.
     * Mobile data cannot be turned off starting Android 4.2.
     */
    @Test
    public void test_NoNetwork_DefaultMessage() {
        toggleWifi(false);

        onView(withId(R.id.loading_progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.refresh_fab)).check(matches(not(isDisplayed())));

        sleep(10000);

        onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.fortune_message_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.fortune_message_text_view)).check(matches(withText("Today is the day to show someone you care.")));

        onView(withId(R.id.refresh_fab)).check(matches(isDisplayed()));

        toggleWifi(true);
    }

    private void toggleWifi(boolean enable) {
        WifiManager wifi = (WifiManager) InstrumentationRegistry.getTargetContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(enable);
    }
}
