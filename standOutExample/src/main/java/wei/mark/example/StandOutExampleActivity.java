package wei.mark.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import wei.mark.standout.StandOutWindow;

public class StandOutExampleActivity extends Activity {

    private static final String TAG = "StandOutExampleActivity";


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create ");
//		StandOutWindow.closeAll(this, MultiWindow.class);
        StandOutWindow.closeAll(this, SimpleWindow.class);
//        StandOutWindow.closeAll(this, WidgetsWindow.class);
//        StandOutWindow.close(this, WidgetsWindow.class, 1);


        // show a MultiWindow, SimpleWindow

		StandOutWindow.show(this, SimpleWindow.class, 0);

//		StandOutWindow.show(this, MultiWindow.class, StandOutWindow.DEFAULT_ID);

//        StandOutWindow.show(this, WidgetsWindow.class, 1);

        finish();

        // show a MostBasicWindow. It is commented out because it does not
        // support closing.

		/*
		 * StandOutWindow.show(this, StandOutMostBasicWindow.class,
		 * StandOutWindow.DEFAULT_ID);
		 */

    }


    @Override
    protected void onPause() {
        Log.d(TAG, "on pause activity");

        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "on create ");

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "on destroy ");

        super.onDestroy();
    }
}