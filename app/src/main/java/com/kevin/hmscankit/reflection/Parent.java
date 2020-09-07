package com.kevin.hmscankit.reflection;

import android.util.Log;

/**
 * @author Kevin  2020/9/7
 */
public class Parent {
    private static final String TAG = "Parent";
    public String publicField="1";
    String defaultField="2";
    protected String protectField="3";
    private String privateField="4";

    public void publicMethod(){
        Log.e(TAG, "publicMethod: "+publicField);
    }

    void defaultMethod() {
        Log.d(TAG, "DefaultField: "+defaultField);
    }
    protected void protectMethod(){
        Log.d(TAG, "protectMethod: "+protectField);
    }
    private void privateMethod(){
        Log.e(TAG, "privateMethod: "+privateField);
    }
}
