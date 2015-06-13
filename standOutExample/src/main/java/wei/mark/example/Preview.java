package wei.mark.example;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Asaf on 04/06/2015.
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Preview";

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Context mContext;
    private Handler mHandler;

    public Preview(Context context, Camera camera) {
        super(context);
        Log.d(TAG, "preview started");
        mCamera = camera;
        mContext = context;
        mHandler = new Handler();
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


//        //This needs to work!!!!
//        OrientationEventListener oel = new OrientationEventListener(context) {
//            @Override
//            public void onOrientationChanged(int i) {
//
//            }
//        };
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        if (mCamera == null) {
            mCamera = WidgetsWindow.getCameraInstance();
        }

//        mHolder = holder;
        // The Surface has been created, now tell the camera where to draw the preview.
        Log.d(TAG, "surface Created and ready to use");
        try {
            Log.d(TAG, "holder is " + holder);
            if (!holder.isCreating()) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }


        try {
            mCamera.startPreview();

            Log.d(TAG, "mCamera.startPreview()");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        Log.d(TAG, "surfaceDestroyed");
        try {
            mCamera.release();
            mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d(TAG, "surfaceChanged");
        mHolder = holder;

        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            Log.d(TAG, "mHolder.getSurface() == null");
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
            Log.d(TAG, "mCamera.stopPreview()");
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {

            mCamera.setPreviewDisplay(mHolder);
//
            mCamera.setDisplayOrientation(90);
//
            mCamera.startPreview();
            Log.d(TAG, "mCamera.startPreview()");

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


}
