package edu.centenary.neuraid.don;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import com.actionbarsherlock.app.SherlockFragmentActivity;





public class ActionBarPracticetwoActivity extends SherlockFragmentActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main_activity, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_save:
	            // app icon in action bar clicked; go home
	        	FragmentManager fm = getSupportFragmentManager();
	    		CourseEntryDialog ceDialog = new CourseEntryDialog();
	    		ceDialog.show(fm, "fragment_edit_name");	
	    		Log.d("Click View", "Just opened dialog for course entry");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}