package co.nimblehq.recentapps.thumbnailhiding;

import android.content.*;
import android.util.Log;

/**
 * https://stackoverflow.com/questions/34471366/detect-touch-event-of-navigation-buttons-inside-a-service-having-window
 */
public class HardwareKeyWatcher {

    private static final String TAG = "HardwareKeyWatcher";
    private Context mContext;
    private IntentFilter mFilter;
    private OnHardwareKeysPressedListener mListener;
    private InnerReceiver mReceiver;

    public interface OnHardwareKeysPressedListener {
        void onHomePressed();

        void onRecentAppsPressed();
    }

    public HardwareKeyWatcher(Context context) {
        mContext = context;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mFilter.setPriority(1000);
    }

    public void setOnHardwareKeysPressedListenerListener(OnHardwareKeysPressedListener listener) {
        mListener = listener;
        mReceiver = new InnerReceiver();
    }

    public void startWatch() {
        if (mReceiver != null) {
            mContext.registerReceiver(mReceiver, mFilter);
        }
    }

    public void stopWatch() {
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    class InnerReceiver extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            Log.d(TAG, "onHomePressed");
                            mListener.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            Log.d(TAG, "onRecentAppsPressed");
                            mListener.onRecentAppsPressed();
                        }
                    }
                }
            }
        }
    }
}
