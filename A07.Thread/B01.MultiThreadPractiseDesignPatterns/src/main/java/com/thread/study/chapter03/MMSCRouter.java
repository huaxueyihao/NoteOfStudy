package com.thread.study.chapter03;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MMSCRouter {

    private static volatile MMSCRouter instance = new MMSCRouter();

    private final Map<String,MMSCInfo> routeMap;

    public MMSCRouter() {
        this.routeMap = MMSCRouter.retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        Map<String,MMSCInfo> map = new HashMap<>();
        // 省略其他代码
        return null;
    }

    public static MMSCRouter getInstance(){
        return instance;
    }

    public MMSCInfo getMMSC(String msisdnPrefix){
        return routeMap.get(msisdnPrefix);
    }

    public static Map<String,MMSCInfo> deepCopy(Map<String,MMSCInfo> m){
        Map<String,MMSCInfo> result = new HashMap<>();
        for (String key : m.keySet()) {
            result.put(key,new MMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String,MMSCInfo> getRouteMap(){
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }

    public static void setInstance(MMSCRouter newInstance){
        instance = newInstance;
    }


}
