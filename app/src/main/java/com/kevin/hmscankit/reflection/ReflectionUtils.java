package com.kevin.hmscankit.reflection;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kevin  2020/9/7
 */
public class ReflectionUtils {
    private static final String TAG = "ReflectionUtils";
    private static final boolean DEBUG = true;

    private ReflectionUtils() {
        //no instance
    }


    private static int mCount;
    /**
     * 循环向上转型，获取对象的定义方法
     *
     * @param obj            子类对象
     * @param methodName     方法名
     * @param parameterTypes 父类的方法参数类型
     */
    public static Method getDeclareMethod(Object obj, String methodName, Class<?>... parameterTypes) {
        Method method;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz.getSuperclass()) {
            try {
                Log.d(TAG, "getDeclareMethod: "+(++mCount)+"  clazz:"+clazz);
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里什么都不要做，并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则不会执行clazz=clazz.getSuperClass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法，而忽略修饰符
     *
     * @param obj            子类对象
     * @param methodName     父类中的方法名
     * @param parameterTypes 父类中的方法参数类型
     * @param parameters     父类中的方法参数
     * @return 父类方法执行的结果
     */

    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        //根据对象、方法名和对应的方法参数，通过反射调用上面的方法获取Method对象
        Method method = getDeclareMethod(obj, methodName, parameterTypes);
        if (method != null) {
            //抑制java对方法进行检查，主要针对私有方法而言
            method.setAccessible(true);
            try {
                return method.invoke(obj, parameters);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 循环向上转型，获取对象定义的属性
     *
     * @param obj       子类对象
     * @param fieldName 父类中的属性名
     * @return 父类中的属性对象
     */


    public static Field getDeclareField(Object obj, String fieldName) {
        Field field;
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {
                //这里什么都不要做，并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则不会执行clazz=clazz.getSuperClass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值
     *
     * @param obj       子类对象
     * @param fieldName 属性名字
     * @param value     将要设置的值
     */
    public void setFieldValue(Object obj, String fieldName, Object value) {
        Field field = getDeclareField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 直接获取对象属性值
     *
     * @param obj       子类对象
     * @param fieldName 属性名字
     * @return 父类中的属性值
     */
    public Object getFieldValue(Object obj, String fieldName) {
        Field field = getDeclareField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
