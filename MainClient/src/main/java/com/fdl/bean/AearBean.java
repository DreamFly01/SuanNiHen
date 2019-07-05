package com.fdl.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/2/13<p>
 * <p>changeTime：2019/2/13<p>
 * <p>version：1<p>
 */
public class AearBean implements IPickerViewData {
    public int Id;
    public String Name;
    public boolean State;
    public List<City> Cities;

    @Override
    public String getPickerViewText() {
        return this.Name;
    }

    public class City{
        public int Id;
        public String Name;
        public boolean State;
        public List<Aera> Areas;
    }

    public class Aera{
        public Aera(int id, String name, boolean state) {
            Id = id;
            Name = name;
            State = state;
        }

        public int Id;
        public String Name;
        public boolean State;
    }
}
