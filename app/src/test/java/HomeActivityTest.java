import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenuItem;

import br.com.lucasalbuquerque.notes.HomeActivity;
import br.com.lucasalbuquerque.notes.NoteActivity;
import br.com.lucasalbuquerque.notes.R;
import br.com.lucasalbuquerque.notes.model.Note;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk=18, manifest="app/src/main/AndroidManifest.xml")
public class HomeActivityTest
{
    HomeActivity homeActivity;

    @Before
    public void buildActivity()
    {
        homeActivity = Robolectric.buildActivity(HomeActivity.class).create().get();
    }

    @Test
    public void verifyThatActivityIsNotNull()
    {
        Assert.assertNotNull(homeActivity);
    }

    @Test
    public void verifyThatInitialNoteListIsEmpty()
    {
        Assert.assertTrue(homeActivity.isNoteListEmpty());
    }

    @Test
    public void verifyThatEmptyListTextAppears()
    {
        TextView emptyListText = (TextView) homeActivity.findViewById(R.id.empty_list_text);

        Assert.assertEquals("No notes yet", emptyListText.getText());
    }

    @Test
    public void verifyThatNoteActivityIsCalledWhenClickingOnAddButton()
    {
        MenuItem addMenu = new TestMenuItem(R.id.action_add);
        homeActivity.onOptionsItemSelected(addMenu);

        Intent intent = Robolectric.shadowOf(homeActivity).peekNextStartedActivity();
        Assert.assertEquals(NoteActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }
}
