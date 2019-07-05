package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/6<p>
 * <p>changeTime：2019/5/6<p>
 * <p>version：1<p>
 */
public class TravelBean {
    public int count;
    public String description;
    public List<PrizePlate> result;
    public int isOpen;
    public class PrizePlate{
        public String LevelCode;
        public String LevelName;
        public String LevelExplain;
        public String LevelImg;
    }
}
