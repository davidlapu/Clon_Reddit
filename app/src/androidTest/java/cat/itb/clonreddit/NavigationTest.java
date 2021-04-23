package cat.itb.clonreddit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    private final LoginRegisterTest loginRegisterTest = new LoginRegisterTest();
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    public void setUp() {
        loginRegisterTest.onlyLogin();
    }

    @Test
    public void goToPostTextFragment() {
        clickOn(R.id.post);
        clickOn(R.id.imageView4);
        assertDisplayed(R.id.fragmentTextPost);
    }

    @Test
    public void goToPostImageFragmentAndSelectASubrredit() {
        clickOn(R.id.post);
        clickOn(R.id.imageView2);
        clickOn(R.id.chooseSubredditArrow);
        assertDisplayed(R.id.fragmentLayoutChoseCommunity);
    }

    @Test
    public void goToCommentSectionOfAPost() {
        /*onView(withId(R.id.recyclerViewMain)).perform((1,
                onView(withId(R.id.commentButton))));*/
    }
}
