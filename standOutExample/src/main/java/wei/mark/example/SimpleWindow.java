package wei.mark.example;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class SimpleWindow extends StandOutWindow {

    @Override
    public String getAppName() {
        return "SimpleWindow";
    }

    @Override
    public int getAppIcon() {
        return android.R.drawable.ic_menu_close_clear_cancel;
    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        // create a new layout from body.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.simple, frame, true);

        FrameLayout flFrameLayout = (FrameLayout) view.findViewById(R.id.floating_icon);
        flFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StandOutWindow.show(getApplicationContext(), WidgetsWindow.class, 1);
                StandOutWindow.close(getApplicationContext(), SimpleWindow.class,
                        StandOutWindow.DEFAULT_ID);
            }
        });

    }

    // the window will be centered
    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, StandOutLayoutParams.WRAP_CONTENT, StandOutLayoutParams.WRAP_CONTENT,StandOutLayoutParams.LEFT,StandOutLayoutParams.CENTER);
    }

    // move the window by dragging the view
    @Override
    public int getFlags(int id) {
        return super.getFlags(id) | StandOutFlags.FLAG_BODY_MOVE_ENABLE
                | StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE

                ;
    }

    //	StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE
    @Override
    public String getPersistentNotificationMessage(int id) {
        return "Click to close the SimpleWindow";
    }

    @Override
    public Intent getPersistentNotificationIntent(int id) {
        return StandOutWindow.getCloseIntent(this, SimpleWindow.class, id);
    }
}