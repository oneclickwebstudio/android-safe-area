package com.getcapacitor.community.safearea;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.view.WindowInsetsCompat;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class SafeArea {
    private final Bridge bridge;
    private final View view;

    public SafeArea(Bridge bridge) {
        Activity activity = bridge.getActivity();
        this.bridge = bridge;
        this.view = activity.findViewById(android.R.id.content).getRootView();
    }

    public void getInsets(PluginCall call) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            view.setOnApplyWindowInsetsListener((v, windowInsets) -> {
                float density = getDensity();
                Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                Log.i(SafeAreaPlugin.class.toString(), String.valueOf(windowInsets.getStableInsetBottom()));
                Log.i(SafeAreaPlugin.class.toString(), String.valueOf(insets.bottom));
                int top = Math.round(insets.top / density);
                int left = Math.round(insets.left / density);
                int right = Math.round(insets.right / density);
                int bottom = Math.round(insets.bottom / density);
                call.resolve(this.result(top, left, right, bottom));
                Log.i(SafeAreaPlugin.class.toString(), this.result(top, left, right, bottom).toString());
                return windowInsets;
            });
        } else {
            call.resolve(this.result(24, 0, 0, 0));
        }
    }

    private JSObject result(int top, int left, int right, int bottom) {
        JSObject json = new JSObject();
        json.put("top", top);
        json.put("left", left);
        json.put("right", right);
        json.put("bottom", bottom);
        return json;
    }

    private float getDensity() {
        return this.bridge.getActivity().getResources().getDisplayMetrics().density;
    }
}
