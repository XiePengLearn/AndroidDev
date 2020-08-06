package com.xiaoanjujia.common.util;

import android.app.Activity;
import android.content.Intent;

import com.xiaoanjujia.common.BaseApplication;

import java.util.Stack;

public class AppManager {
    private static Stack<Activity> activityStack;

    private static class SingletonHolder {
        private static final AppManager INSTANCE = new AppManager();
    }

    private AppManager() {

    }

    /**
     * 单例模式
     *
     * @return
     */
    public static final AppManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加 Activity 到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取class对应的Activity
     *
     * @param cls 对应的Activity.class文件
     * @return 返回.class对应的Activity, 不存在此文件时返回空
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 获取当前 Activity （堆栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.size() == 0) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前 Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的 Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的 Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退到指定activity
     */
    public void finishOthersToActivity(Class<?> cls) {
        while (!activityStack.empty()) {
            Activity activity = activityStack.lastElement();
            if (activity.getClass().equals(cls)) {
                break;
            }
            finishActivity(activity);
        }
    }

    /**
     * 退到指定activity
     */
    public void finishOthersToActivity(Class<?> cls,boolean isContain) {
        while (!activityStack.empty()) {
            Activity activity = activityStack.lastElement();
            if (activity.getClass().equals(cls)) {
                if (isContain){
                    finishActivity(activity);
                }
                break;
            }
            finishActivity(activity);
        }
    }

    public void finishOthersToActivityAndStart(Class<?> cls, boolean isContain, Intent intent){
        while (!activityStack.empty()) {
            Activity activity = activityStack.lastElement();
            if (activity.getClass().equals(cls)) {
                if (isContain){
                    finishActivity(activity);
                }
                break;
            }
            finishActivity(activity);
        }
        BaseApplication.getInstance().startActivity(intent);
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivityWithoutTop() {
        for (int i = 0, size = activityStack.size(); i < size - 1; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
