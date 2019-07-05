package com.fdl.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/23<p>
 * <p>changeTime：2019/1/23<p>
 * <p>version：1<p>
 */
public class MapDataBean implements Serializable {
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
