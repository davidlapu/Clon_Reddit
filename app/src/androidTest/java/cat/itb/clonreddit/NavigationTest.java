package cat.itb.clonreddit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
        clickOn(R.id.browse);
        clickOn(R.id.chat);
        clickOn(R.id.inbox);
        clickOn(R.id.home);
        assertDisplayed(R.id.recyclerViewMain);

    }
}
