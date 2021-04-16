package cat.itb.clonreddit;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class LoginRegisterTest {
    private final String USER = "david", PASS= "12345678",
            EMAIL_REGISTER = System.currentTimeMillis()+"david@itb.cat",
            EMAIL_LOGIN = "davidlama@itb.cat";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void register() {
        onView(withId(R.id.emailHomeScreenButton)).perform(click());

        onView(withId(R.id.emailEditText)).perform(typeText(EMAIL_REGISTER), closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText(USER), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText(PASS), closeSoftKeyboard());
        onView(withId(R.id.continueBTN)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
        logout();
    }

    @Test
    public void login() {
        onView(withId(R.id.loginHomeScreen)).perform(click());
        onView(withId(R.id.usernameEditText)).perform(typeText(EMAIL_LOGIN), closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText(PASS), closeSoftKeyboard());
        onView(withId(R.id.continueBTN)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
        logout();
    }


    public void logout() {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open());
        onView(withId(R.id.logOutButton)).perform(click());
    }
}
