package com.getcapacitor.community.safearea;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class SafeArea {
    private final Activity activity;
    Insets windowInsets;
    float dp;

    public SafeArea(Bridge bridge) {
        activity = bridge.getActivity();
    }

    public void getInsets(PluginCall call) {
        View view = this.activity.findViewById(android.R.id.content).getRootView();
        JSObject ret = new JSObject();
        WindowInsetsCompat windowInsetsCompat = ViewCompat.getRootWindowInsets(view);
        windowInsets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        dp = Resources.getSystem().getDisplayMetrics().density;
        ret.put("top", windowInsets.top / dp);
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            ret.put("bottom", windowInsets.bottom / dp);
        } else {
            ret.put("bottom", 0);
        }
        view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Update your app's layout based on the new window insets
                // For example, you can adjust the padding of your views to account for the system UI insets
                ret.put("top", insets.getStableInsetTop() / dp);
                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                    v.setPadding(0, 0, 0, (int) (-1 * windowInsets.bottom));
                    ret.put("bottom", insets.getInsets(v.getId()).bottom / dp);
                } else {
                    v.setPadding(0, 0, 0, 0);
                    ret.put("bottom", 0);
                }
                call.resolve(ret);
                return insets;
            }
        });
        call.resolve(ret);
    }
}
