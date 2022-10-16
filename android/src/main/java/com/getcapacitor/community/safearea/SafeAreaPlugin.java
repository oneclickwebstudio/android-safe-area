package com.getcapacitor.community.safearea;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "SafeArea")
public class SafeAreaPlugin extends Plugin {

    private SafeArea implementation;

    @Override
    public void load() {
        implementation = new SafeArea(this.bridge);
    }

    @PluginMethod
    public void getInsets(PluginCall call) {
        implementation.getInsets(call);
    }
}
