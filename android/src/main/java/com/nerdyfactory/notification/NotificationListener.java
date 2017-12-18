package com.nerdyfactory.notification;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.facebook.react.bridge.WritableNativeMap;

public class NotificationListener extends NotificationListenerService {
    private static final String TAG = "NotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "Notification received: "+sbn.getPackageName()+":"+sbn.getNotification().tickerText);

        if (sbn.getNotification().tickerText == null) {
            return;
        }

        WritableNativeMap params = new WritableNativeMap();
        params.putString("text", sbn.getNotification().tickerText.toString());
        params.putString("color", Integer.toHexString(sbn.getNotification().color).substring(2));
        params.putString("info", sbn.getNotification().toString());

        String app = sbn.getPackageName();
        if (app.equals(NotificationModule.smsApp)) {
            params.putString("app", "sms");
        } else {
            params.putString("app", app);
        }

        NotificationModule.sendEvent("notificationReceived", params);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "Notification deleted: "+sbn.getPackageName()+":"+sbn.getNotification().tickerText);

        if (sbn.getNotification().tickerText == null) {
            return;
        }

        WritableNativeMap params = new WritableNativeMap();
        params.putString("text", sbn.getNotification().tickerText.toString());
        params.putString("color", Integer.toHexString(sbn.getNotification().color).substring(2));
        params.putString("info", sbn.getNotification().toString());

        String app = sbn.getPackageName();
        if (app.equals(NotificationModule.smsApp)) {
            params.putString("app", "sms");
        } else {
            params.putString("app", app);
        }

        NotificationModule.sendEvent("notificationDeleted", params);
    }
}
