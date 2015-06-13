package wei.mark.example;


import android.hardware.Camera;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import wei.mark.standout.ui.Window;

public class WidgetsWindow extends MultiWindow {
    public static final int DATA_CHANGED_TEXT = 0;

    private static final String TAG = "WidgetsWindow";
    private static Camera mCamera;
    private Preview mPreview;
    private boolean isCamOpen = false;

    @Override
    public void createAndAttachView(final int id, FrameLayout frame) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widgets, frame, true);



        // Create our Preview view and set it as the content of our activity.
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.floating_cam_container);
        // Create an instance of Camera
        mCamera = getCameraInstance();
        if (mCamera != null) {
            isCamOpen = true;
        }


        if (isCamOpen) {

            mPreview = new Preview(this, mCamera);

            preview.addView(mPreview);


        } else {
            Log.d(TAG, "Camera not open");
        }
    }


    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 400, 300,
                StandOutLayoutParams.TOP, StandOutLayoutParams.CENTER);
    }

    @Override
    public String getAppName() {
        return "WidgetWindow";
    }

    @Override
    public int getThemeStyle() {
        return android.R.style.Theme_Light;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Log.d(TAG, "getCameraInstance");
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance

            Log.d(TAG, "Camera opened");

        } catch (Exception e) {
            String message = "Camera is not available (in use or does not exist)";

            //Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
        return c; // returns null if camera is unavailable
    }


    public static Camera getmCamera() {
        return mCamera;
    }

    public static void setmCamera(Camera mCamera) {
        WidgetsWindow.mCamera = mCamera;
    }
}