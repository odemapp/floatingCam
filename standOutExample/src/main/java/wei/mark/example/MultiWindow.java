package wei.mark.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

/**
 * This implementation provides multiple windows. You may extend this class or
 * use it as a reference for a basic foundation for your own windows.
 * <p/>
 * <p/>
 * Functionality includes system window decorators, moveable, resizeable,
 * hideable, closeable, and bring-to-frontable.
 * <p/>
 * <p/>
 * The persistent notification creates new windows. The hidden notifications
 * restores previously hidden windows.
 *
 * @author Mark Wei <markwei@gmail.com>
 */
public class MultiWindow extends StandOutWindow {

    private static final int CAMERAZOOM = 5;
    private static String TAG = "MultiWindow";

    @Override
    public String getAppName() {
        return "MultiWindow";
    }

    @Override
    public int getAppIcon() {
//		return android.R.drawable.ic_menu_add;
        return 0;
    }

    @Override
    public String getTitle(int id) {
        return "";
//		return getAppName() + " " + id;

    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        // create a new layout from body.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.body, frame, true);

        TextView idText = (TextView) view.findViewById(R.id.id);
        idText.setText(String.valueOf(id));
    }

    // every window is initially same size
    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 400, 300,
                StandOutLayoutParams.AUTO_POSITION,
                StandOutLayoutParams.AUTO_POSITION, 100, 100);
    }

    // we want the system window decorations, we want to drag the body, we want
    // the ability to hide windows, and we want to tap the window to bring to
    // front
    @Override
    public int getFlags(int id) {
        return   StandOutFlags.FLAG_WINDOW_ASPECT_RATIO_ENABLE
                | StandOutFlags.FLAG_BODY_MOVE_ENABLE
                | StandOutFlags.FLAG_WINDOW_HIDE_ENABLE
                | StandOutFlags.FLAG_DECORATION_SYSTEM

                ;
    }

    //TODO gil removed this flags
//	| StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE
//	| StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP
//	| StandOutFlags.FLAG_WINDOW_PINCH_RESIZE_ENABLE
//    | StandOutFlags.FLAG_ADD_FUNCTIONALITY_DROP_DOWN_DISABLE
//    StandOutFlags.FLAG_DECORATION_SYSTEM

    @Override
    public String getPersistentNotificationTitle(int id) {
        return getAppName() + " Running";
    }

    @Override
    public String getPersistentNotificationMessage(int id) {
        return "Click to add a new " + getAppName();
    }

    // return an Intent that creates a new MultiWindow
    @Override
    public Intent getPersistentNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), getUniqueId());
    }

    @Override
    public int getHiddenIcon() {
        return android.R.drawable.ic_menu_info_details;
    }

    @Override
    public String getHiddenNotificationTitle(int id) {
        return getAppName() + " Hidden";
    }

    @Override
    public String getHiddenNotificationMessage(int id) {
        return "Click to restore #" + id;
    }

    // return an Intent that restores the MultiWindow
    @Override
    public Intent getHiddenNotificationIntent(int id) {
        return StandOutWindow.getShowIntent(this, getClass(), id);
    }

    @Override
    public Animation getShowAnimation(int id) {
        if (isExistingId(id)) {
            // restore
            return AnimationUtils.loadAnimation(this,
                    android.R.anim.slide_in_left);
        } else {
            // show
            return super.getShowAnimation(id);
        }
    }

    @Override
    public Animation getHideAnimation(int id) {
        return AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
    }

    // this is the "action bar menu" - here i will do :
    // zoom in\out
    // flash light on\off
    // close the app
    @Override
    public List<DropDownListItem> getDropDownItems(int id) {

        //getting the active camera instance
        android.hardware.Camera Mcamera = null;
        try {
            Mcamera = WidgetsWindow.getmCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final android.hardware.Camera finalMcamera = Mcamera;


        List<DropDownListItem> items = new ArrayList<DropDownListItem>();
        items.add(new DropDownListItem(android.R.drawable.btn_plus,
                "zoom in", new Runnable() {


            @Override
            public void run() {


                android.hardware.Camera.Parameters params = finalMcamera.getParameters();
                int maxZoomLevel = params.getMaxZoom();
                Log.d(TAG, "max zoom is " + maxZoomLevel);
                int currentZoom = params.getZoom();
                if (currentZoom < maxZoomLevel) {
                    currentZoom += CAMERAZOOM;
                    params.setZoom(currentZoom);
                    finalMcamera.setParameters(params);
                }


            }
        }));

        //TODO gil created this
        items.add(new DropDownListItem(android.R.drawable.btn_minus,
                "zoom out", new Runnable() {

            @Override
            public void run() {


                android.hardware.Camera.Parameters params = finalMcamera.getParameters();
                int maxZoomLevel = params.getMaxZoom();
                Log.d(TAG, "max zoom is " + maxZoomLevel);
                int currentZoom = params.getZoom();
                if (currentZoom > 0) {
                    currentZoom -= CAMERAZOOM;
                    params.setZoom(currentZoom);
                    finalMcamera.setParameters(params);
                }

            }
        }));


        items.add(new DropDownListItem(android.R.drawable.btn_star,
                "Flash light", new Runnable() {

            @Override
            public void run() {


                int flashLight = 0;

                android.hardware.Camera.Parameters params = finalMcamera.getParameters();

                String flashMode = params.getFlashMode();

                if (flashMode.equalsIgnoreCase(android.hardware.Camera.Parameters.FLASH_MODE_OFF)) {
                    flashLight = 0;
                    params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);

                    finalMcamera.setParameters(params);


                } else if (flashMode.equalsIgnoreCase(android.hardware.Camera.Parameters.FLASH_MODE_TORCH)) {

                    params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                    finalMcamera.setParameters(params);
                }


            }
        }));
        return items;
    }


    @Override
    public void onReceiveData(int id, int requestCode, Bundle data,
                              Class<? extends StandOutWindow> fromCls, int fromId) {
        // receive data from WidgetsWindow's button press
        // to show off the data sending framework
        switch (requestCode) {
            case WidgetsWindow.DATA_CHANGED_TEXT:
                Window window = getWindow(id);
                if (window == null) {
                    String errorText = String.format(Locale.US,
                            "%s received data but Window id: %d is not open.",
                            getAppName(), id);
                    Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
                    return;
                }
                String changedText = data.getString("changedText");
                TextView status = (TextView) window.findViewById(R.id.id);
                status.setTextSize(20);
                status.setText("Received data from WidgetsWindow: "
                        + changedText);
                break;
            default:
                Log.d("MultiWindow", "Unexpected data received.");
                break;
        }
    }
}
