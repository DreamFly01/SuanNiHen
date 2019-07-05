package com.fdl.utils;

import com.fdl.bean.EventBusEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Administrator on 2019/5/31 0031 16:46
 * jy
 * 46
 */
public class EventBusUtils {

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void sendEvent(EventBusEvent event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(EventBusEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 通过code码区分事件类型
     */
    public static final class EventCode {
        public static final int A = 0x111111;
        public static final int B = 0x222222;
        public static final int C = 0x333333;
        public static final int D = 0x444444;
        public static final int E = 0x555555;
        // other more
    }
}

