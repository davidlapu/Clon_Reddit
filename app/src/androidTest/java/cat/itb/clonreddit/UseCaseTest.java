package cat.itb.clonreddit;



import android.view.KeyEvent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.rule.BaristaRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;
import cat.itb.clonreddit.fragments.CommentPostFragment;
import kotlin.jvm.JvmField;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild;

@RunWith(AndroidJUnit4.class)
public class UseCaseTest {
    private final String TEST_TEXT = "Dani";
    private final String TITLE_POST_TEST  = "Post de testing";
    private final String BODY_POST_TEST  = "Creando un post de texto para testear su ejecucion";
    private final String COMMENT_TEXT_TEST = "Creando un comentario de prueba";
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public BaristaRule<MainActivity> baristaRule = BaristaRule.create(MainActivity.class);



    @Test
    public void filtrar_recyclerView(){
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText(TEST_TEXT), pressKey(KeyEvent.KEYCODE_ENTER), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void create_TextPost(){
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
        onView(withId(R.id.recyclerViewSubrredit)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.editTextTitleTextPost)).perform(typeText(TITLE_POST_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPost)).perform(typeText(BODY_POST_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonPostText)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }


//    Para volver a probar este test accede a la clase LoginRegister.java y ejecuta el test LogIn() para evitar que pete
    @Test
    public void make_a_comment(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickListItemChild(R.id.recyclerViewMain, 0, R.id.commentButton);
        onView(withId(R.id.commentPostFragment)).check(matches(isDisplayed()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.addCommentButton)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editTextTextComment)).perform(typeText(COMMENT_TEXT_TEST), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonPostText)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.commentPostFragment)).check(matches(isDisplayed()));
        //Si vuelve a este fragment, se ha comentado con éxito

    }
}
