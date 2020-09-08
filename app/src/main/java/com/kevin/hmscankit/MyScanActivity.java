package com.kevin.hmscankit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.hmsscankit.RemoteView;
import com.kevin.hmscankit.reflection.Parent;
import com.kevin.hmscankit.reflection.ReflectionUtils;
import com.kevin.hmscankit.reflection.Son;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyScanActivity extends AppCompatActivity {
    private static final String TAG = "MyScanActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scan);
        Log.d(TAG, "onCreate: ");
        // Todo 通过创建RemoteView方式无效，该构造函数未包访问权限
        //RemoteView mRemoteView;
        //int scanFormatValue;
        //if (this.getIntent() != null) {
        //     scanFormatValue= this.getIntent().getIntExtra("ScanFormatValue", 0);
        //    mRemoteView=new  RemoteView(this,false,scanFormatValue,null);
        //}

        //Test Reflection
        //Son son = new Son();
        //Method publicMethod = ReflectionUtils.getDeclareMethod(son, "publicMethod");
        //Log.d(TAG, "onCreate: 公共方法:" + publicMethod.getName());
        //Method defaultMethod = ReflectionUtils.getDeclareMethod(son, "defaultMethod");
        //Log.d(TAG, "onCreate: 默认方法"+defaultMethod.getName());
        //Method protectMethod=ReflectionUtils.getDeclareMethod(son,"protectMethod");
        //Log.d(TAG, "onCreate: 保护方法"+protectMethod);
        //Method privateMethod=ReflectionUtils.getDeclareMethod(son,"privateMethod");
        //Log.d(TAG, "onCreate: 私有方法"+privateMethod);

        Son son = new Son();
        Class<?> clazz = son.getClass();
        Class<?> parent = clazz.getSuperclass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            //Field privateField=clazz.getDeclaredField("privateField");
            Field privateField = parent.getDeclaredField("privateField");
            Method method = parent.getDeclaredMethod("privateMethod");
            method.setAccessible(true);
            method.invoke(son,"publicMethod");
            //method.invoke(parent,"privateMethod",null,null);
            Log.d(TAG, "onCreate: " + privateField.getName());
            Log.d(TAG, "onCreate: " + method.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: clazz:" + clazz);
        Log.d(TAG, "onCreate: parent:" + parent);
        Log.d(TAG, "onCreate: fields:" + fields.length);
        for (Field f : fields) {
            Log.d(TAG, "onCreate: " + f.getName());
        }

        //Todo 测试新的方式
        //try {
        //    Foo foo = new Foo("建立一个测试对象");
        //    //Class<?> clazz = foo.getClass();
        //    Class<?> clazz = foo.getClass();
        //    Method m1 = clazz.getDeclaredMethod("outInfo");
        //    Method m2 = clazz.getDeclaredMethod("setMsg",String.class);
        //    Method m3 = clazz.getDeclaredMethod("getMsg");
        //    m1.invoke(foo);
        //    m2.invoke(foo,"设置新的set值");
        //    String newValue= (String) m3.invoke(foo);
        //    Log.d(TAG, "onCreate: "+newValue);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    class Foo {
        private String msg;

        public Foo(String msg) {
            this.msg = msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void outInfo() {
            Log.d(TAG, "outInfo: 这是测试Java反射的测试类");
        }
    }
}