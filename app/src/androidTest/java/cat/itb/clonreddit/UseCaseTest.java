package cat.itb.clonreddit;


import android.view.KeyEvent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;

/**
 * Activity 5
 * Finally, we will return to the use cases that you defined in activity 1 and we will implement each of them
 * as a different test function. First of all create a new test class called "UseCaseTest".
 * For each use case, create a test function where you constantly check the interactions expected
 * from the user and how the application should react to those interactions.
 * Make sure that the names of the functions are meaningful and that by reading them you can understand
 * what is being tested in each case.
 */
@RunWith(AndroidJUnit4.class)
public class UseCaseTest {
    private final String TEST_TEXT = "Dani";
    private final String TITLE_POST_TEST  = "Post de testing";
    private final String BODY_POST_TEST  = "Creando un post de texto para testear su ejecucion";
    private final String COMMENT_TEXT_TEST = "Creando un comentario de prueba";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    /**
     * Activity 4
     * We are going to do a test to a RecyclerView of your application, the one you prefer
     * (preferably one that when you click you can see the detail of the element).
     * The test function must click on a specific position in your RecyclerView (the one you choose) and once clicked,
     * check that the corresponding activity/fragment is displayed and that the information it contains is as expected.
     */
    @Test
    public void click_recycler_and_check_if_all_is_in_order(){
        sleep(1000);
        clickListItemChild(R.id.recyclerViewMain, 0, R.id.commentButton);
        onView(withId(R.id.commentPostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewTitlePost)).check(matches(withText("Dani no me pone las ideses")));
        sleep(2000);
    }



    @Test
    public void filtrar_recyclerView(){
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText(TEST_TEXT), pressKey(KeyEvent.KEYCODE_ENTER), ViewActions.closeSoftKeyboard());
        sleep(1000);
    }



    @Test
    public void create_TextPost(){
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.imageView4)).perform(click());
        sleep(1000);
        onView(withId(R.id.textPostFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.chooseSubredditTextView)).perform(click());
        onView(withId(R.id.subRedditListFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewSubrredit)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.editTextTitleTextPost)).perform(typeText(TITLE_POST_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPost)).perform(typeText(BODY_POST_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonPostText)).perform(click());
        sleep(1000);
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }



    @Test
    public void make_a_comment(){
        sleep(1000);
        clickListItemChild(R.id.recyclerViewMain, 0, R.id.commentButton);
        onView(withId(R.id.commentPostFragment)).check(matches(isDisplayed()));
        sleep(1000);
        onView(withId(R.id.addCommentButton)).perform(click());
        sleep(1000);
        onView(withId(R.id.editTextTextComment)).perform(typeText(COMMENT_TEXT_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonPostText)).perform(click());
        sleep(1000);
        onView(withId(R.id.commentPostFragment)).check(matches(isDisplayed()));
    }



    @Test
    public void give_a_Like(){
        clickListItemChild(R.id.recyclerViewMain, 0, R.id.upVoteButton);
        sleep(2000);
    }


    @Test
    public void logOut(){
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open());
        onView(withId(R.id.logOutButton)).perform(click());
    }




}
