package cat.itb.clonreddit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.internal.failurehandler.BaristaException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;

@RunWith(AndroidJUnit4.class)
public class UseCaseTest {
    private final String TITLE_POST = "autoTestBarista", CONTENT_POST = "TestTestTest",
        POST_TO_SEARCH = "test empty";
    private final LoginRegisterTest loginRegisterTest = new LoginRegisterTest();
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        try {
            assertDisplayed(R.id.homeScreenFragment);
            loginRegisterTest.onlyLogin();
        } catch (BaristaException ignored) {}
    }

    @Test
    public void createTextPost() {
        clickOn(R.id.post);
        clickOn(R.id.imageView4);
        assertDisplayed(R.id.fragmentTextPost);

        clickOn(R.id.chooseSubredditArrow);
        assertDisplayed(R.id.fragmentLayoutChoseCommunity);
        clickListItem(R.id.recyclerViewSubrredit, 1);

        assertDisplayed(R.id.fragmentTextPost);
        writeTo(R.id.editTextTitleTextPost, TITLE_POST);
        writeTo(R.id.editTextTextPost, CONTENT_POST);
        clickOn(R.id.buttonPostText);
    }

    @Test
    public void createComment() {
        sleep(2000);

        clickListItemChild(R.id.recyclerViewMain, 2, R.id.commentButton);
        assertDisplayed(R.id.fragmentLayoutCommentPost);

        clickOn(R.id.addCommentButton);
        assertDisplayed(R.id.fragmentLayoutCreateComment);
        writeTo(R.id.editTextTextComment, CONTENT_POST);
        clickOn(R.id.buttonPostText);

        assertDisplayed(R.id.fragmentLayoutCommentPost);
    }

    @Test
    public void upVoteAndDownVotePost() {
        sleep(2000);

        clickListItemChild(R.id.recyclerViewMain, 2, R.id.upVoteButton);
        assertDisplayedAtPosition(R.id.recyclerViewMain, 2, R.id.textViewUpVotePost, "1");
        clickListItemChild(R.id.recyclerViewMain, 2, R.id.downVoteButton);
        assertDisplayedAtPosition(R.id.recyclerViewMain, 2, R.id.textViewUpVotePost, "0");
    }

    @Test
    public void SearchPostInMainScreen() {
        writeTo(R.id.searchView, POST_TO_SEARCH);
        sleep(1000);

        assertDisplayedAtPosition(R.id.recyclerViewMain, 0, R.id.textViewTitlePost, POST_TO_SEARCH);
    }

    @Test
    public void logoutFromNavigationDrawer() {
        openDrawer();
        clickOn(R.id.logOutButton);
        assertDisplayed(R.id.homeScreenFragment);
    }


}
