package cat.itb.clonreddit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.internal.failurehandler.BaristaException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.itb.clonreddit.activities.MainActivity;

import static com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsClosed;
import static com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsOpen;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.closeDrawer;
import static com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer;
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {
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
    public void goToPostTextFragment() {
        clickOn(R.id.post);
        clickOn(R.id.imageView4);
        assertDisplayed(R.id.fragmentTextPost);
    }

    @Test
    public void goToPostImageFragmentAndSelectASubrredit() {
        clickOn(R.id.post);
        clickOn(R.id.imageView2);
        assertDisplayed(R.id.fragmentLayoutImagePost);
        clickOn(R.id.chooseSubredditArrow);
        assertDisplayed(R.id.fragmentLayoutChoseCommunity);
    }

    @Test
    public void openDrawerLayoutAndCloseIt() {
        assertDrawerIsClosed();
        openDrawer();
        assertDrawerIsOpen();
        closeDrawer();
        assertDrawerIsClosed();
    }

    @Test
    public void goToCommentSectionOfAPostAndCreateACommentFragment() {
        sleep(1500);
        clickListItemChild(R.id.recyclerViewMain, 0, R.id.commentButton);
        assertDisplayed(R.id.fragmentLayoutCommentPost);

        clickOn(R.id.addCommentButton);
        assertDisplayed(R.id.fragmentLayoutCreateComment);
    }

    @Test
    public void testBottomMenu() {
        sleep(1000);
        assertDisplayed(R.id.recyclerViewMain);
        clickOn(R.id.browse);
        assertDisplayed(R.id.imageContent);
        clickOn(R.id.chat);
        assertDisplayed(R.id.imageContent);
        clickOn(R.id.inbox);
        assertDisplayed(R.id.imageContent);
        clickOn(R.id.home);
        assertDisplayed(R.id.recyclerViewMain);
    }
}
