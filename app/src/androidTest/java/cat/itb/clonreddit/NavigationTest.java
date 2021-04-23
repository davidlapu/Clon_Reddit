package cat.itb.clonreddit;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    private final String USER_TO_BE_TYPED = "admin";
    private final String PASS_TO_BE_TYPED = "123456789";

    private final String EMAIL_LOGIN_TO_BE_TYPED = "logintest@gmail.com";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void MainFragment_to_TextPostFragment(){
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView4)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textPostFragment)).check(matches(isDisplayed()));
    }


    @Test
    public void MainFragment_to_TextPostFragment_to_SubRedditListFragment(){
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView4)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.textPostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.chooseSubredditTextView)).perform(click());
        onView(withId(R.id.subRedditListFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void MainFragment_to_ImagePostFragment(){
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView2)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imagePostFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void MainFragment_to_ImagePostFragment_to_SubRedditListFragment_back_to_ImagePostFragment_back_to_MainFragment_logOut(){
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView2)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imagePostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.chooseSubredditTextView)).perform(click());
        onView(withId(R.id.subRedditListFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.closeBTN)).perform(click());
        onView(withId(R.id.imagePostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.closeBTN)).perform(click());
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open());
        onView(withId(R.id.logOutButton)).perform(click());
    }




    @Test
    public void HomeScreenFragment_to_MainFragment_to_ImagePostFragment_to_SubRedditListFragment(){
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
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView2)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imagePostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.chooseSubredditTextView)).perform(click());
        onView(withId(R.id.subRedditListFragment)).check(matches(isDisplayed()));
    }
}
