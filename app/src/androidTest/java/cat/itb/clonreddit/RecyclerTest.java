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
import static com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;

@RunWith(AndroidJUnit4.class)
public class RecyclerTest {
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
    public void testRecyclerView() {
        sleep(2000);

        assertDisplayedAtPosition(R.id.recyclerViewMain, 3, R.id.textViewTitlePost, "David nubb");

        clickListItemChild(R.id.recyclerViewMain, 3, R.id.upVoteButton);
        assertDisplayedAtPosition(R.id.recyclerViewMain, 3, R.id.textViewUpVotePost, "2");
        clickListItemChild(R.id.recyclerViewMain, 3, R.id.downVoteButton);
        assertDisplayedAtPosition(R.id.recyclerViewMain, 3, R.id.textViewUpVotePost, "1");

        clickListItemChild(R.id.recyclerViewMain, 3, R.id.commentButton);

        sleep(1500);

        assertDisplayed(R.id.fragmentLayoutCommentPost);

        assertDisplayed(R.id.textViewSubredditNamePost, "r/ProgrammerHumor");
        assertDisplayed(R.id.textViewAwardsPost, "0 Awards");
        assertDisplayed(R.id.textViewTitlePost, "David nubb");
        assertDisplayed(R.id.textViewTextPost, "Reusman else empanado");
        assertDisplayed(R.id.textViewUpVotePost, "1");
        assertDisplayed(R.id.commentButton, "0");
    }

}
