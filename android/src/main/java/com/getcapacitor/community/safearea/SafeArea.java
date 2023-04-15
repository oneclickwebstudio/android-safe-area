package com.getcapacitor.community.safearea;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class SafeArea {
    private final Bridge bridge;
    private final Activity activity;

    public SafeArea(Bridge bridge) {
        this.activity = bridge.getActivity();
        this.bridge = bridge;
    }

    public void getInsets(PluginCall call) {
        WindowInsets windowInsets = bridge.getActivity().getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Log.i(SafeAreaPlugin.class.toString(), "WindowInsets is not available.");
            call.resolve(this.result(0, 0, 0, 0));
            return;
        }

        float density = this.getDensity();
        int top = Math.round(windowInsets.getStableInsetTop() / density);
        int left = Math.round(windowInsets.getStableInsetLeft() / density);
        int right = Math.round(windowInsets.getStableInsetRight() / density);
        int bottom = Math.round(windowInsets.getStableInsetBottom() / density);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            bottom = 0;
        }

        call.resolve(this.result(top, left, right, bottom));
        this.startWindowInsetsListener(call);
    }

    private JSObject result(int top, int left, int right, int bottom) {
        JSObject json = new JSObject();
        json.put("top", top);
        json.put("left", left);
        json.put("right", right);
        json.put("bottom", bottom);
        return json;
    }

    private void startWindowInsetsListener(PluginCall call) {
        View view = this.activity.findViewById(android.R.id.content).getRootView();
        view.setOnApplyWindowInsetsListener((v, windowInsets) -> {
            v.setPadding(0, 0, 0, 0);
            float density = getDensity();
            int top = Math.round(windowInsets.getStableInsetTop() / density);
            int left = Math.round(windowInsets.getStableInsetLeft() / density);
            int right = Math.round(windowInsets.getStableInsetRight() / density);
            int bottom = Math.round(windowInsets.getStableInsetBottom() / density);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                Log.i(SafeAreaPlugin.class.toString(), this.result(top, left,right,bottom).toString());
                call.resolve(this.result(top, left, right, bottom));
            } else {
                call.resolve(this.result(0, 0, 0, 0));
            }

            return windowInsets;
        });
    }

    private float getDensity() {
        return this.bridge.getActivity().getResources().getDisplayMetrics().density;
    }
}
