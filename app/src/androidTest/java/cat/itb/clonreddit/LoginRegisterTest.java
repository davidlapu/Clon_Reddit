package cat.itb.clonreddit;

import androidx.annotation.ContentView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginRegisterTest {
    private final String EMAIL_TO_BE_TYPED = System.currentTimeMillis() + "admin@gmail.com";
    private final String USER_TO_BE_TYPED = "admin";
    private final String PASS_TO_BE_TYPED = "123456789";

    private final String EMAIL_LOGIN_TO_BE_TYPED = "logintest@gmail.com";



    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void register(){
        onView(withId(R.id.emailHomeScreenButton)).perform(click());
        onView(withId(R.id.emailEditText)).perform(typeText(EMAIL_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.usernameEditText)).perform(typeText(USER_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText(PASS_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.continueBTN)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }



    @Test
    public void logIn(){
        onView(withId(R.id.loginHomeScreen)).perform(click());
        onView(withId(R.id.usernameEditText)).perform(typeText(EMAIL_LOGIN_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText(PASS_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.continueBTN)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }


    @Test
    public void logOut(){
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open());
        onView(withId(R.id.logOutButton)).perform(click());
    }
}
