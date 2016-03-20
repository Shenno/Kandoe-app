package be.kdg.teamf.kandoe_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by admin on 19/03/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void homeFragmentDisplayed() {
        onView(withId(R.id.textViewHome)).check(matches(isDisplayed()));
    }

    @Test
    public void openCloseMenu() {
        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.nav_view)).check(matches((isDisplayed())));
        onView(withId(R.id.drawer_layout)).perform(close());
        onView(withId(R.id.nav_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void registerAccountLoginIn() {
        //Registreer account
        String email = randomString() + "@gmail.com";
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Registreer")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editTextFirstname)).perform(typeText("Shenno"));
        onView(withId(R.id.editTextLastname)).perform(typeText("Willaert"));
        onView(withId(R.id.editTextEmail)).perform(typeText(email));
        onView(withId(R.id.editTextPwd)).perform(typeText("shenno"));
        onView(withId(R.id.editTextPwdConfirm)).perform(typeText("shenno"));
        onView(withId(R.id.buttonSubmit)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.textView_email)).check(matches(withText(email)));
        onView(withId(R.id.textViewHome)).check(matches(isDisplayed()));
        onView(withText("Log uit")).check(matches(isDisplayed())).perform(click());

        //Log in
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Log in")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editTextEmail)).perform(typeText(email));
        onView(withId(R.id.editTextPwd)).perform(typeText("shenno"));
        onView(withId(R.id.buttonSubmit)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withId(R.id.textView_email)).check(matches(withText(email)));
        onView(withId(R.id.textViewHome)).check(matches(isDisplayed()));
        onView(withText("Log uit")).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void sessionOverview() {
        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Log in")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.editTextEmail)).perform(typeText("scott.tiger@live.com"));
        onView(withId(R.id.editTextPwd)).perform(typeText("scott"));
        onView(withId(R.id.buttonSubmit)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Speel sessie")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.my_recycler_view))
                .check(matches(hasDescendant(withText("Seeded sessie"))));

        onView(withId(R.id.drawer_layout)).perform(open());
        onView(withText("Log uit")).check(matches(isDisplayed())).perform(click());
    }

    public String randomString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
