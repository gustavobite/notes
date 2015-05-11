import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenuItem;

import br.com.lucasalbuquerque.notes.NoteActivity;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18, manifest="src/main/AndroidManifest.xml")
public class NoteActivityTest
{
    NoteActivity noteActivity;

    @Before
    public void buildActivity()
    {
        noteActivity = Robolectric.buildActivity(NoteActivity.class).create().get();
    }

    @Test
    public void verifyThatActivityIsNotNull()
    {
        Assert.assertNotNull(noteActivity);
    }

    @Test
    public void verifyThatHomeActivityIsCalledWhenClickingOnHomeButton()
    {
        MenuItem homeMenu = new TestMenuItem(android.R.id.home);
        noteActivity.onOptionsItemSelected(homeMenu);

        assertTrue(Robolectric.shadowOf(noteActivity).isFinishing());
    }
}
