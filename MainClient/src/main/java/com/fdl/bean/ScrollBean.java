package com.fdl.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/1/25<p>
 * <p>changeTime：2019/1/25<p>
 * <p>version：1<p>
 */

public class ScrollBean extends SectionEntity<ScrollBean.ScrollItemBean> {
    public  int SupplierId;
    public ScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }
    public ScrollItemBean itemBean;

    public ScrollBean(ScrollBean.ScrollItemBean bean) {
        super(bean);
    }

    public static class ScrollItemBean {
        public Long CommTenantId;
        public String CommTenantName;
        public String CommTenantIcon;
        public int OnThePin;
        public double Price;
        public String UnitsTitle;
        public int GoodsNum;

        public long Inventory;
        public int total;
        public String type;

        public ScrollItemBean(String test, String type) {
            this.type = type;
        }

        public ScrollItemBean() {
        }
    }

}
