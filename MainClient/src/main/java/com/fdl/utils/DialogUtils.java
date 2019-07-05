package com.fdl.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdl.activity.buy.OrderActivity;
import com.fdl.activity.buy.PayActivity;
import com.fdl.activity.goTravel.TravelActivity;
import com.fdl.activity.wool.WoolDetailsActivity;
import com.fdl.adapter.AearsAdapter;
import com.fdl.adapter.ChoseAdapter;
import com.fdl.adapter.ChoseBankAdapter;
import com.fdl.adapter.CouponAdapter;
import com.fdl.adapter.DialogShopCartAdapter;
import com.fdl.adapter.MyChoseBankAdapter;
import com.fdl.adapter.ParamsAdapter;
import com.fdl.adapter.SkuAdapter;
import com.fdl.bean.AreasBean;
import com.fdl.bean.BanksBean;
import com.fdl.bean.BaseResultBean;
import com.fdl.bean.ChoseBean;
import com.fdl.bean.NormBean;
import com.fdl.bean.ProductDetailsBean;
import com.fdl.bean.ShakyBean;
import com.fdl.bean.WoolBean;
import com.fdl.bean.daoBean.AearBean;
import com.fdl.bean.daoBean.CommTenant;
import com.fdl.bean.daoBean.MyBankBean;
import com.fdl.db.DBManager;
import com.fdl.requestApi.NetSubscriber;
import com.fdl.requestApi.RequestClient;
import com.fdl.wedgit.PayPsdInputView;
import com.fdl.wedgit.RecycleViewDivider;
import com.gyf.barlibrary.ImmersionBar;
import com.sg.cj.common.base.utils.SgLog;
import com.sg.cj.snh.R;
import com.snh.greendao.AearBeanDao;
import com.snh.greendao.CommTenantDao;
import com.snh.greendao.DaoMaster;
import com.snh.greendao.DaoSession;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * <p>desc：弹窗工具类<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2018/9/29<p>
 * <p>changeTime：2018/9/29<p>
 * <p>version：1<p>
 */
public class DialogUtils {

    private Activity mActivity;
    private Context mContext;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private View v;
    private Dialog dialog;
    private boolean flag;
    private ConfirmClickLisener confirmClickLisener;
    private ChoseClickLisener choseClickLisener;
    private HeadImgChoseLisener headImgChoseLisener;
    private OnItemClick onItemClick;
    private AddressChose onAddressChose;
    private Address1Chose onAddress1hose;
    public static DialogUtils instance;
    private AddressQg onAddressQgClick;


    public interface ConfirmClickLisener {
        void onConfirmClick(View v);
    }

    public interface ChoseClickLisener {
        void onConfirmClick(View v);

        void onCancelClick(View v);
    }

    public interface HeadImgChoseLisener {
        void onCancelClick(View v);

        void onPhotoClick(View v);

        void onAlumClick(View v);
    }

    public interface OnItemClick {
        void onItemClick(View v, int position);
    }

    public interface AddressChose {
        void onAddressChose(AreasBean bean);

        void onAddressChose(String id, String address, String level);

        void onLatylyChose(String id, String address, String level);
    }

    public interface Address1Chose {
        void onAddressChose(AreasBean bean);
    }

    public interface AddressQg {
        void onAddressQg(String address);
    }

    public static DialogUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DialogUtils(context);
        }
        return instance;
    }

    public DialogUtils(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    public DialogUtils(Context context) {
        this.mContext = context;
        this.mActivity = (Activity) context;
    }

    public DialogUtils noBtnDialog(String content) {

        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_simple_layout, null);
        Button comfirm = (Button) v.findViewById(R.id.btn_comfirm);


        TextView txcontent = (TextView) v.findViewById(R.id.iv_dialog_content);
        txcontent.setText(content);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);

        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return this;
    }

    /**
     * 简单弹窗
     *
     * @param content
     * @param flag
     */
    public DialogUtils simpleDialog(String content, final ConfirmClickLisener confirmClickLisener, boolean flag) {
        this.confirmClickLisener = confirmClickLisener;
        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_simple_layout, null);
        Button comfirm = (Button) v.findViewById(R.id.btn_comfirm);


        TextView txcontent = (TextView) v.findViewById(R.id.iv_dialog_content);
        txcontent.setText(content);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClickLisener.onConfirmClick(view);
            }
        });


        return this;
    }

    /**
     * 有确定取消按钮弹窗
     *
     * @param content
     * @param choseClickLisener
     * @param flag
     * @return
     */
    public DialogUtils twoBtnDialog(String content, final ChoseClickLisener choseClickLisener, boolean flag) {
        this.choseClickLisener = choseClickLisener;
        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_two_btn_layout, null);
        Button comfirm = (Button) v.findViewById(R.id.btn_comfirm);
        Button cancle = (Button) v.findViewById(R.id.btn_cancel);
        TextView txcontent = (TextView) v.findViewById(R.id.iv_dialog_content);
        txcontent.setText(content);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseClickLisener.onConfirmClick(view);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseClickLisener.onCancelClick(view);
            }
        });

        return this;
    }

    /**
     * 头像选择弹窗
     *
     * @param
     * @param headImgChoseLisener
     * @param flag
     */

    public DialogUtils headImgDialog(final HeadImgChoseLisener headImgChoseLisener, boolean flag) {
        this.headImgChoseLisener = headImgChoseLisener;
        //自定义样式使得弹窗铺满全屏
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_headimg_layout, null);
        TextView cancel = (TextView) v.findViewById(R.id.btn_cancel);
        TextView photo = (TextView) v.findViewById(R.id.btn_photograph);
        TextView album = (TextView) v.findViewById(R.id.btn_album);


        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headImgChoseLisener.onPhotoClick(view);
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headImgChoseLisener.onAlumClick(view);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headImgChoseLisener.onCancelClick(view);
            }
        });
        return this;
    }

    /**
     * 自定义选择弹窗
     *
     * @param onItemClick
     * @param mTop
     * @param mRight
     * @param width
     * @param datas
     * @return
     */
    public DialogUtils customDialog(final OnItemClick onItemClick, int mTop, int mRight, int width, List<String> datas) {
        this.onItemClick = onItemClick;
        builder = new AlertDialog.Builder(mContext, R.style.dialog);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_custom_layout, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_dialog_custom);
        com.fdl.adapter.CustomAdapter adapter = new com.fdl.adapter.CustomAdapter(mContext, R.layout.item_dialog_custom, datas);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                onItemClick.onItemClick(view, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        dialog = builder.create();
        dialog.setContentView(v);
        dialog.show();


        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = mRight;//设置x坐标
        params.y = mTop;//设置y坐标
        params.width = width;
        params.height = WRAP_CONTENT;
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        window.setAttributes(params);
        dialog.getWindow().setContentView(v, params);
        return this;
    }


    /**
     * 分享弹窗
     *
     * @return
     */
    public DialogUtils ShareDialog(String title, String url, String introduce, String imgUrl) {
        this.confirmClickLisener = confirmClickLisener;
        //自定义样式使得弹窗铺满全屏
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_buttom_layout, null);
        TextView cancel = (TextView) v.findViewById(R.id.btn_cancel);
        ImageView friend = (ImageView) v.findViewById(R.id.share_friend);
        ImageView commends = (ImageView) v.findViewById(R.id.share_commends);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StrUtils.isEmpty(url)) {
                    return;
                }
                if (url.contains("http")) {
                    ShareUtils.ShareWechat(title, url, introduce, imgUrl);//分享好友
                } else {
                    ShareUtils.ShareWechctApplets(title, url, introduce, imgUrl);//分享小程序
                }
                SgLog.d("title:" + title + "  url:" + url + "  introduce:" + introduce + "  imgurl:" + imgUrl);
                dialog.dismiss();
            }
        });

        commends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.ShareWechatMom(title, url, introduce, imgUrl);//分享朋友圈
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return this;
    }


    /**
     * 规格选择弹窗
     */
    int numStr = 1;
    int InventoryStr = 0;

    TextView remainNum;
    TextView num;
    TextView price;
    List<Integer> listId;

    public DialogUtils SukDilogButtom(int intFlag, int goodsId, final ConfirmClickLisener confirmClickLisener, ProductDetailsBean bean) {
        this.confirmClickLisener = confirmClickLisener;
        numStr = 1;
        InventoryStr = 0;
        //自定义样式使得弹窗铺满全屏
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_suk_layout, null);
        ImageView close = (ImageView) v.findViewById(R.id.iv_close);
        TextView comfire = (TextView) v.findViewById(R.id.tv_comfire);
        ImageView logo = (ImageView) v.findViewById(R.id.iv_product_logo);
        ImageView add = (ImageView) v.findViewById(R.id.iv_add);
        ImageView delete = (ImageView) v.findViewById(R.id.iv_delete);
        TextView name = (TextView) v.findViewById(R.id.tv_name);
        price = (TextView) v.findViewById(R.id.tv_price);
        remainNum = (TextView) v.findViewById(R.id.tv_remain_num);
        num = (TextView) v.findViewById(R.id.tv_num);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_suk);
        Map<Integer, Integer> normsIdmap = new TreeMap<>();
        Map<Integer, String> normsInfoMap = new TreeMap<>();

        listId = new ArrayList<>();
        for (int i = 0; i < bean.SKU.size(); i++) {
            listId.add(bean.SKU.get(i).ShopNormsValuesListView.get(0).Id);
        }
        if (bean.SKU.size() > 0) {
            SkuAdapter adapter = new SkuAdapter(mContext, R.layout.item_dialog_sku_layout, bean.SKU, new SkuAdapter.DataListener() {
                @Override
                public void send(int position, int NormsIds, String NormsInfos) {
                    normsIdmap.put(position, NormsIds);
                    normsInfoMap.put(position, NormsInfos);
//                    for (int i = 0; i < bean.SKU.size(); i++) {
//                        listId.add(bean.SKU.get(i).ShopNormsValuesListView.get(0).Id);
//                    }
                    listId.clear();
                    for (int i = 0; i < normsIdmap.size(); i++) {
                        listId.add(normsIdmap.get(i));
                    }
                    getNormsPrice(goodsId, StringUtils.join(listId, ",", 0, listId.size()), bean);

                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(adapter);
        }

        ImageUtils.loadUrlImage(mContext, bean.CoverImgId, logo);
        name.setText(bean.Name);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InventoryStr > 0) {
                    InventoryStr = InventoryStr - 1;
                    numStr = numStr + 1;
                    num.setText(numStr + "");
//                    remainNum.setText("(剩余:" + InventoryStr + ")");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numStr > 0) {
                    InventoryStr = InventoryStr + 1;
                    numStr = numStr - 1;
                    num.setText(numStr + "");
//                    remainNum.setText("(剩余:" + InventoryStr + ")");
                }
            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);

        comfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(num.getText().toString().trim()) > 0) {

                    listId = new ArrayList<>();
                    List<String> listNorm = new ArrayList<>();
                    for (int i = 0; i < normsIdmap.size(); i++) {
                        listId.add(normsIdmap.get(i));
                        listNorm.add(normsInfoMap.get(i));
                    }

                    NormsId = StringUtils.join(listId, ",", 0, listId.size());
                    normsStringId = StringUtils.join(listNorm, ",", 0, listNorm.size());
                    switch (intFlag) {
                        case 1:
                            if (bean.SKU.size() != normsIdmap.size()) {
                                Toast.makeText(mContext, "请选择规格", Toast.LENGTH_SHORT).show();
                            } else {

                                confirmClickLisener.onConfirmClick(view);
                                Number = Integer.parseInt(num.getText().toString());
                                List<String> listInfo = new ArrayList<>();


                                for (int i = 0; i < normsInfoMap.size(); i++) {
                                    listInfo.add(normsInfoMap.get(i));
                                }
                                if (listInfo.size() > 0) {
                                    String info1 = listInfo.toString().replace("{", "");
                                    NormsInfo = info1.replace("}", "");
                                } else {
                                    NormsInfo = "";
                                }
                                addProductToCar(NormsId, goodsId, bean);
                                dialog.dismiss();

                            }
                            break;
                        case 2:
                            if (bean.SKU.size() != normsIdmap.size()) {
                                Toast.makeText(mContext, "请选择规格", Toast.LENGTH_SHORT).show();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("goodsId", goodsId + "");
                                bundle.putString("GoodsNum", numStr + "");
                                bundle.putString("GoodsNormId", NormsId);
                                bundle.putString("shopLogo", bean.shop.ShopLogo);
                                bundle.putString("CoverImg", bean.CoverImgId);
                                bundle.putString("NormsInfo", normsStringId);
                                bundle.putString("totalPrice", normPrice * Integer.parseInt(num.getText().toString()) + "");
                                JumpUtils.dataJump((Activity) mContext, OrderActivity.class, bundle, false);
                                dialog.dismiss();
                            }

                            break;
                    }

                }
            }

        });


        getNormsPrice(goodsId, StringUtils.join(listId, ",", 0, listId.size()), bean);

        return this;
    }

    public DialogUtils CouponDialog() {
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_coupon_layout, null);
        LinearLayout back = v.findViewById(R.id.rl_01);
        LinearLayout heard = (LinearLayout) inflater.inflate(R.layout.item_heart_coupone_layout, null);
        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        CouponAdapter adapter = new CouponAdapter(R.layout.item_coupon_layout, datas);
        adapter.addHeaderView(heard);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        dialog = builder.create();
        dialog.show();


        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) ((int) display.getWidth() * 0.95);
        layoutParams.height = (int) (display.getHeight() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    public DialogUtils ParamsDialog() {
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_params_layout, null);

        LinearLayout back = v.findViewById(R.id.rl_01);
        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        ParamsAdapter adapter = new ParamsAdapter(R.layout.item_params_layout, datas);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        dialog = builder.create();
        dialog.show();


        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }


    private List<AreasBean> areasBeans = new ArrayList<>();
    List<AearBean> aear1 = new ArrayList<>();
    List<AearBean> aear2 = new ArrayList<>();
    List<AearBean> aear3 = new ArrayList<>();
    List<AreasBean> data1 = new ArrayList<>();
    List<AreasBean> data2 = new ArrayList<>();
    List<AreasBean> data3 = new ArrayList<>();
    AreasBean dataBean;

    LinearLayout ll01;
    LinearLayout ll02;
    LinearLayout ll03, liTitle;
    RelativeLayout llDis;
    TextView tv01;
    TextView tv02;
    TextView tv03;
    ImageView iv01;
    ImageView iv02;
    ImageView iv03;
    TextView address1;
    TextView address2;
    TextView addressQg;

    public DialogUtils Address1Dialog(Address1Chose addressChose) {
        onAddress1hose = addressChose;
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AearBeanDao aearBeanDao = daoSession.getAearBeanDao();
//        !SPUtils.getInstance(mContext).getBoolean(Contans.ADDRESS)
        if (!SPUtils.getInstance(mContext).getBoolean(Contans.ADDRESS)) {
            initJson();
        }
        data1.clear();

        aear1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Level.eq("1")).list();
        for (int i = 0; i < aear1.size(); i++) {
            dataBean = new AreasBean();
            dataBean.AddressName = aear1.get(i).AddressName;
            dataBean.id = aear1.get(i).id;
            dataBean.Level = aear1.get(i).Level;
            dataBean.ParentID = aear1.get(i).ParentID;
            data1.add(dataBean);
        }


        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_areas1_layout, null);
        ll01 = v.findViewById(R.id.ll_01);
        ll02 = v.findViewById(R.id.ll_02);
        ll03 = v.findViewById(R.id.ll_03);
        tv01 = v.findViewById(R.id.tv_01);
        tv02 = v.findViewById(R.id.tv_02);
        tv03 = v.findViewById(R.id.tv_03);
        iv01 = v.findViewById(R.id.iv_01);
        iv02 = v.findViewById(R.id.iv_02);
        iv03 = v.findViewById(R.id.iv_03);

        ll01.setEnabled(false);
        ll02.setEnabled(false);
        ll03.setEnabled(false);

        AearsAdapter adapter1 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        AearsAdapter adapter2 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        AearsAdapter adapter3 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        RecyclerView recyclerView1 = v.findViewById(R.id.recyclerView1);
        RecyclerView recyclerView2 = v.findViewById(R.id.recyclerView2);
        RecyclerView recyclerView3 = v.findViewById(R.id.recyclerView3);


        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView1.setAdapter(adapter1);
        adapter1.setNewData(data1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView3.setAdapter(adapter3);

        dialog = builder.create();
        dialog.show();


        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.5);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);

        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                data2.clear();

                aear2 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aear1.get(position).id)).list();
                for (int i = 0; i < aear2.size(); i++) {
                    dataBean = new AreasBean();
                    dataBean.AddressName = aear2.get(i).AddressName;
                    dataBean.id = aear2.get(i).id;
                    dataBean.Level = aear2.get(i).Level;
                    dataBean.ParentID = aear2.get(i).ParentID;
                    data2.add(dataBean);
                    recyclerView1.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    recyclerView3.setVisibility(View.GONE);
                }
                adapter1.setIsChose(position);
                adapter2.setIsChose(-1);
                adapter2.setNewData(data2);
                setViewVisible(tv02, iv02);
                ll01.setEnabled(true);
                tv01.setText(data1.get(position).AddressName);
                tv02.setText("请选择市");
                tv03.setText("请选择区/县");
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                aear3 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aear2.get(position).id)).list();
                data3.clear();

                for (int i = 0; i < aear3.size(); i++) {
                    dataBean = new AreasBean();
                    dataBean.AddressName = aear3.get(i).AddressName;
                    dataBean.id = aear3.get(i).id;
                    dataBean.Level = aear3.get(i).Level;
                    dataBean.ParentID = aear3.get(i).ParentID;
                    data3.add(dataBean);
                }
                adapter3.setNewData(data3);
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                setViewVisible(tv03, iv03);
                ll02.setEnabled(true);
                ll03.setEnabled(true);
                adapter2.setIsChose(position);

//                tv01.setText(data1.get(position).AddressName);
                tv02.setText(data2.get(position).AddressName);
                tv03.setText("请选择市");
            }


        });
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onAddress1hose.onAddressChose(data3.get(position));
                dialog.dismiss();
            }
        });

        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                setViewVisible(tv01, iv01);
            }
        });
        ll02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.GONE);
                setViewVisible(tv02, iv02);
            }
        });
        ll03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                setViewVisible(tv03, iv03);
            }
        });
        return this;
    }

    /**
     * 首页定位区县
     */

    public DialogUtils AddressDialog(AddressChose addressChose, AddressQg onAddressQg) {


        onAddressChose = addressChose;
        onAddressQgClick = onAddressQg;
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AearBeanDao aearBeanDao = daoSession.getAearBeanDao();
        if (!SPUtils.getInstance(mContext).getBoolean(Contans.ADDRESS)) {
            initJson();
        }
        data1.clear();

        aear1 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.Level.eq("1")).list();
        for (int i = 0; i < aear1.size(); i++) {
            dataBean = new AreasBean();
            dataBean.AddressName = aear1.get(i).AddressName;
            dataBean.id = aear1.get(i).id;
            dataBean.Level = aear1.get(i).Level;
            dataBean.ParentID = aear1.get(i).ParentID;
            data1.add(dataBean);
        }

        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_areas_layout, null);
        ll01 = v.findViewById(R.id.ll_01);
        ll02 = v.findViewById(R.id.ll_02);
        ll03 = v.findViewById(R.id.ll_03);
        tv01 = v.findViewById(R.id.tv_01);
        tv02 = v.findViewById(R.id.tv_02);
        tv03 = v.findViewById(R.id.tv_03);
        iv01 = v.findViewById(R.id.iv_01);
        iv02 = v.findViewById(R.id.iv_02);
        iv03 = v.findViewById(R.id.iv_03);
        llDis = v.findViewById(R.id.ll_dimiss);
        liTitle = v.findViewById(R.id.li_title);
        ll01.setEnabled(false);
        ll02.setEnabled(false);
        ll03.setEnabled(false);

        address1 = v.findViewById(R.id.tv_address1);
        address2 = v.findViewById(R.id.tv_address2);
        addressQg = v.findViewById(R.id.tv_addressqg);
        if (StrUtils.isEmpty(SPUtils.getInstance(mContext).getString(Contans.CITY))) {
            address1.setVisibility(View.GONE);
        } else {
            address1.setVisibility(View.VISIBLE);
            address1.setText(SPUtils.getInstance(mContext).getString(Contans.CITY));
        }
        if (StrUtils.isEmpty(SPUtils.getInstance(mContext).getString(Contans.LATELY_CITY))) {
            address2.setText("无访问");
            address2.setEnabled(false);
        } else {
            address2.setEnabled(true);
            address2.setText(SPUtils.getInstance(mContext).getString(Contans.LATELY_CITY));
        }

        AearsAdapter adapter1 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        AearsAdapter adapter2 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        AearsAdapter adapter3 = new AearsAdapter(R.layout.item_dialog_address_layout, null);
        RecyclerView recyclerView1 = v.findViewById(R.id.recyclerView1);
        RecyclerView recyclerView2 = v.findViewById(R.id.recyclerView2);
        RecyclerView recyclerView3 = v.findViewById(R.id.recyclerView3);

        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView1.setAdapter(adapter1);
        adapter1.setNewData(data1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView3.setAdapter(adapter3);


        dialog = builder.create();
        dialog.show();

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) display.getHeight();
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                data2.clear();
                aear2 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aear1.get(position).id)).list();
                for (int i = 0; i < aear2.size(); i++) {
                    dataBean = new AreasBean();
                    dataBean.AddressName = aear2.get(i).AddressName;
                    dataBean.id = aear2.get(i).id;
                    dataBean.Level = aear2.get(i).Level;
                    dataBean.ParentID = aear2.get(i).ParentID;
                    data2.add(dataBean);
                    recyclerView1.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    recyclerView3.setVisibility(View.GONE);
                }
                adapter1.setIsChose(position);
                adapter2.setIsChose(-1);
                adapter2.setNewData(data2);
                setViewVisible(tv02, iv02);
                ll01.setEnabled(true);
                tv01.setText(data1.get(position).AddressName);
                tv02.setText("请选择市");
                tv03.setText("请选择区/县");
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                aear3 = aearBeanDao.queryBuilder().where(AearBeanDao.Properties.ParentID.eq(aear2.get(position).id)).list();
                data3.clear();

                for (int i = 0; i < aear3.size(); i++) {
                    dataBean = new AreasBean();
                    dataBean.AddressName = aear3.get(i).AddressName;
                    dataBean.id = aear3.get(i).id;
                    dataBean.Level = aear3.get(i).Level;
                    dataBean.ParentID = aear3.get(i).ParentID;
                    data3.add(dataBean);
                }
                adapter3.setNewData(data3);
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                setViewVisible(tv03, iv03);
                ll02.setEnabled(true);
                ll03.setEnabled(true);
                adapter2.setIsChose(position);
                tv02.setText(data2.get(position).AddressName);
                tv03.setText("请选择区/县");
            }


        });
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                addressChose.onAddressChose(data3.get(position));
                dialog.dismiss();
            }
        });

        ll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                setViewVisible(tv01, iv01);
            }
        });
        ll02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.GONE);
                setViewVisible(tv02, iv02);
            }
        });
        ll03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                setViewVisible(tv03, iv03);
            }
        });

        llDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressChose.onAddressChose(SPUtils.getInstance(mContext).getString(Contans.CITY_ID), address1.getText().toString(), SPUtils.getInstance(mContext).getString(Contans.CITY_LEVEL));
                dialog.dismiss();
            }
        });

        address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressChose.onLatylyChose(SPUtils.getInstance(mContext).getString(Contans.LATELY_ADDRESS_ID), address2.getText().toString(), SPUtils.getInstance(mContext).getString(Contans.LATELY_LEVEL));
                dialog.dismiss();
            }
        });

        addressQg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressQgClick.onAddressQg("全国");
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setViewVisible(TextView tv, ImageView iv) {
        tv01.setTextColor(Color.parseColor("#1e1e1e"));
        tv02.setTextColor(Color.parseColor("#1e1e1e"));
        tv03.setTextColor(Color.parseColor("#1e1e1e"));
        iv01.setVisibility(View.INVISIBLE);
        iv02.setVisibility(View.INVISIBLE);
        iv03.setVisibility(View.INVISIBLE);

        tv.setTextColor(Color.parseColor("#fc1a4e"));
        iv.setVisibility(View.VISIBLE);

    }

    public DialogUtils GoodsDetailsDialog() {
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_params_layout, null);

        LinearLayout back = v.findViewById(R.id.rl_01);
        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        ParamsAdapter adapter = new ParamsAdapter(R.layout.item_params_layout, datas);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        dialog = builder.create();
        dialog.show();


        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.8);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    List<AearBean> aearBeans = new ArrayList<>();

    public void initJson() {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        final AearBeanDao aearBeanDao = daoSession.getAearBeanDao();
        String JsonData = new GetJsonDataUtil().getJson(mContext, "aears.json");//获取assets目录下的json文件数据
        aearBeans = JSON.parseArray(JsonData, AearBean.class);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    aearBeanDao.deleteAll();
                    AsyncSession asyncSession = new AsyncSession(daoSession);
                    final List<AearBean> finalAearBeans = aearBeans;
                    asyncSession.runInTx(new Runnable() {
                        @Override
                        public void run() {
                            aearBeanDao.insertOrReplaceInTx(finalAearBeans);
                        }
                    });
                    SPUtils.getInstance(mContext).savaBoolean(Contans.ADDRESS, true).commit();
                }
            }).start();

        } catch (Exception e) {
            Toast.makeText(mContext, "更新地址失败", Toast.LENGTH_SHORT).show();
        }

    }

    public DialogUtils PasswordDialog(final int cardId, final String moneystr, String accrualstr) {
        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_password_layout, null);
        FrameLayout cancle = (FrameLayout) v.findViewById(R.id.fr_cancle);

        final PayPsdInputView psw = (PayPsdInputView) v.findViewById(R.id.et_psw);
        TextView money = (TextView) v.findViewById(R.id.tv_money);
        TextView accrual = (TextView) v.findViewById(R.id.tv_money1);

        money.setText(Double.parseDouble(moneystr) + "");
        accrual.setText(Double.parseDouble(accrualstr) + "");
        psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(psw.getText().toString().length()==6){
//                    Log.d(HttpLogger.LOGKYE,"发起请求！");
//                    RequestClient.VerifyPayPwd(Md5Utils.md5(psw.getText().toString().trim()), mContext, new NetSubscriber<String>(mContext,true) {
//                        @Override
//                        public void onResultNext(String model) {
//                            RequestClient.ExtractBalan(cardId, moneystr, mContext, new NetSubscriber<String>(mContext,false) {
//                                @Override
//                                public void onResultNext(String model) {
//                                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
//                                    imm.hideSoftInputFromWindow(psw.getWindowToken(),0);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt(MainActivity.SHOW_FRAGMENT_INDEX,2);
//                                    JumpUtils.dataJump((Activity) mContext, MainActivity.class,bundle,true);
//                                    Toast.makeText(mContext,"提现申请成功！",Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
//
//                                }
//
//                                @Override
//                                public void onResultErro(APIException erro) {
//                                    super.onResultErro(erro);
//                                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
//                                    imm.hideSoftInputFromWindow(psw.getWindowToken(),0);
//                                    dialog.dismiss();
//                                }
//                            });
//
//                        }
//
//                        @Override
//                        public void onResultErro(APIException erro) {
//                            super.onResultErro(erro);
//                            dialog.dismiss();
//                        }
//                    });
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.width = (int) (display.getWidth()*0.8);
        layoutParams.y = 40;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.TOP);//可以设置显示的位置
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return this;
    }


    /**
     * 弹窗消失
     *
     * @return
     */
    public DialogUtils dismissDialog() {
        if (null != dialog) {
//            ImmersionBar.with(mActivity, dialog).destroy();
            dialog.dismiss();
        }

        return this;
    }

    public DialogUtils outSideIsDismiss(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        return this;
    }
    //添加到购物车

    int SupplierId, Number, ExpressTemplateId;
    String NormsInfo, NormsId, normsStringId;
    double Weight;

    private void addProductToCar(String normsId, int goodsId, ProductDetailsBean bean) {

        RequestClient.AddProductToCar(normsId, bean.SupplierId, goodsId, Number, NormsId, bean.ExpressTemplateId, NormsInfo, bean.Weight, mContext, new NetSubscriber<BaseResultBean>(mContext, true) {
            @Override
            public void onResultNext(BaseResultBean model) {
                Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private DialogShopCartAdapter adapter1;

    public DialogUtils shopCartDialog(final ChoseClickLisener listener, List<CommTenant> datas, int supplierId, int marginBoom, boolean flag) {
        this.choseClickLisener = listener;
        adapter1 = new DialogShopCartAdapter(R.layout.item_dialog_shop_cart_layout, null);
        adapter1.setNewData(datas);
        //自定义样式使得弹窗铺满全屏
        builder = new AlertDialog.Builder(mContext, R.style.Dialog_FS);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_shop_cart_layout, null);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter1);

        dialog = new Dialog(mContext, R.style.shopCartStyle);
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.y = marginBoom;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(v);
//        dialog.getWindow().setWindowAnimations(R.style.shopCartStyle);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        datas.get(position).total -= 1;
                        deleteDataInDb(datas.get(position));
                        choseClickLisener.onConfirmClick(view.findViewById(R.id.iv_delete));
                        if ((datas.get(position).total) <= 0) {
                            datas.remove(position);
                        }
                        break;
                    case R.id.iv_add:
                        if (datas.get(position).total < datas.get(position).Inventory) {
                            datas.get(position).total += 1;
                            addDataToDb(datas.get(position));
                            choseClickLisener.onCancelClick(view.findViewById(R.id.iv_add));
                        }
                        break;
                }
                adapter.setNewData(query(supplierId));
            }
        });
        return this;
    }

    private void addDataToDb(CommTenant beans) {

        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();

        CommTenant commTenant = commTenantDao.load(beans.CommTenantId);

        if (null != commTenant) {
            commTenant = beans;
            try {
                commTenantDao.update(commTenant);
                SgLog.d("添加成功");
            } catch (Exception e) {
                SgLog.d("添加失败");
            }
        } else {
            commTenantDao.insert(commTenant);
        }
    }

    private void deleteDataInDb(CommTenant beans) {

        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        CommTenant commTenant = commTenantDao.load(beans.CommTenantId);
        if (commTenant != null) {
            if (commTenant.total > 1) {
                commTenant = beans;
                try {
                    commTenantDao.update(commTenant);
                    SgLog.d("删除成功");
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            } else {
                try {
                    SgLog.d("删除成功");
                    commTenantDao.delete(commTenant);
                } catch (Exception e) {
                    SgLog.d("删除失败");
                }
            }
        }
        if (commTenantDao.loadAll().size() < 1) {
            dialog.dismiss();
        }

    }

    private List<CommTenant> query(int supplierId) {
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(mContext).getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CommTenantDao commTenantDao = daoSession.getCommTenantDao();
        return commTenantDao.queryBuilder().where(CommTenantDao.Properties.SupplierId.eq(supplierId)).list();
    }

    private double normPrice;
    private int normInventroy;

    private void getNormsPrice(int goodsId, String normsId, ProductDetailsBean bean) {
        if (StrUtils.isEmpty(normsId)) {
            normPrice = bean.SalesPrice;
            normInventroy = (int) bean.Inventory;
            price.setText("¥" + normPrice);
            num.setText(numStr + "");
            InventoryStr = normInventroy;
            if (InventoryStr > 0) {
                num.setText(1 + "");
                InventoryStr -= 1;
            } else {
                num.setText(0 + "");
            }
            remainNum.setText("(剩余:" + normInventroy + ")");
        } else {
            RequestClient.GetNormPrice(goodsId, normsId, mContext, new NetSubscriber<BaseResultBean<NormBean>>(mContext, true) {
                @Override
                public void onResultNext(BaseResultBean<NormBean> model) {
                    normPrice = model.data.NormPrice;
                    normInventroy = (int) model.data.NormInventory;
                    price.setText("¥" + normPrice);
                    num.setText(numStr + "");
                    InventoryStr = normInventroy;
                    if (InventoryStr > 0) {
                        num.setText(1 + "");
                        InventoryStr -= 1;
                    } else {
                        num.setText(0 + "");
                    }
                    remainNum.setText("(剩余:" + normInventroy + ")");
                }
            });
        }

    }

    private ConfirmBankLisener bankLisener;

    public interface ConfirmBankLisener {
        void onConfirmClick(BanksBean bean, int position);
    }

    public DialogUtils bankDiaolog(final ConfirmBankLisener confirmClickLisener, final List<BanksBean> data, boolean flag) {
        bankLisener = confirmClickLisener;
        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_bank_layout, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_bank);
        ChoseBankAdapter adapter = new ChoseBankAdapter(R.layout.item_chose_bank_layout, data);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);


        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = (int) (display.getHeight() * 0.5);

        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bankLisener.onConfirmClick(data.get(position), position);
            }
        });
        return this;
    }

    private ConfirmMyBankLisener myBankLisener;

    public interface ConfirmMyBankLisener {
        void onConfirmClick(MyBankBean bean, int position);
    }

    public DialogUtils myBankDiaolog(final ConfirmMyBankLisener confirmClickLisener, final List<MyBankBean> data, boolean flag) {
        myBankLisener = confirmClickLisener;
        builder = new AlertDialog.Builder(mContext);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_bank_layout, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_bank);
        MyChoseBankAdapter adapter = new MyChoseBankAdapter(R.layout.item_chose_bank_layout, data);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);


        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(flag);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = (int) (display.getHeight() * 0.5);

        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setContentView(v);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                myBankLisener.onConfirmClick(data.get(position), position);
            }
        });
        return this;
    }

    ChoseAdapter adapter;

    public DialogUtils choseDialog(final OnItemClick onItemClick, int mTop, List<ChoseBean> datas) {
        this.onItemClick = onItemClick;
        builder = new AlertDialog.Builder(mContext, R.style.dialog);
        inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.dialog_chose_layout, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_dialog_custom);
        adapter = new ChoseAdapter(R.layout.item_dialog_chose_layout, datas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onItemClick.onItemClick(view, position);
            }
        });

        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.VERTICAL, R.drawable.line_2_gray));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        dialog = builder.create();
        dialog.show();
        dialog.setContentView(v);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.y = mTop;//设置y坐标
        params.width = MATCH_PARENT;
        params.height = WRAP_CONTENT;
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        window.setAttributes(params);
//        dialog.getWindow().setContentView(v, params);
        return this;
    }

    public void setChoseDatas(List<ChoseBean> datas) {
        adapter.setNewData(datas);
    }


}
