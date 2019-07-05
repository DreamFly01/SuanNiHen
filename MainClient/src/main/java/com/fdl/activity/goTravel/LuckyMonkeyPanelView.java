package com.fdl.activity.goTravel;

import android.app.FragmentManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fdl.bean.TravelBean;
import com.fdl.bean.TravelLuckyBean;
import com.sg.cj.snh.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/5/6<p>
 * <p>changeTime：2019/5/6<p>
 * <p>version：1<p>
 */
public class LuckyMonkeyPanelView extends FrameLayout {


    private ImageView bg_1;
    private ImageView bg_2;

    private PanelItemView itemView1, itemView2, itemView3,
            itemView4, itemView6,
            itemView7, itemView8, itemView9;

    private ItemView[] itemViewArr = new ItemView[8];
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;

    private boolean isMarqueeRunning = false;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 150;
    private static final int MIN_SPEED = 50;
    private int currentSpeed = DEFAULT_SPEED;
    private Context context;
    private List<TravelBean.PrizePlate> datas = new ArrayList<>();
    private FragmentManager fragmentManager;
    private TravelLuckyBean bean;


    private MediaPlayer mediaPlayer;


    public LuckyMonkeyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_mokey_panel, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startMarquee();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void setupView() {
//        bg_1 = (ImageView) findViewById(R.id.bg_1);
//        bg_2 = (ImageView) findViewById(R.id.bg_2);
        itemView1 = (PanelItemView) findViewById(R.id.item1);
        itemView2 = (PanelItemView) findViewById(R.id.item2);
        itemView3 = (PanelItemView) findViewById(R.id.item3);
        itemView4 = (PanelItemView) findViewById(R.id.item4);
        itemView6 = (PanelItemView) findViewById(R.id.item6);
        itemView7 = (PanelItemView) findViewById(R.id.item7);
        itemView8 = (PanelItemView) findViewById(R.id.item8);
        itemView9 = (PanelItemView) findViewById(R.id.item9);

        itemViewArr[0] = itemView4;
        itemViewArr[1] = itemView1;
        itemViewArr[2] = itemView2;
        itemViewArr[3] = itemView3;
        itemViewArr[4] = itemView6;
        itemViewArr[5] = itemView9;
        itemViewArr[6] = itemView8;
        itemViewArr[7] = itemView7;
        itemView1.setView(context, datas.get(0).LevelName, datas.get(0).LevelImg);
        itemView2.setView(context, datas.get(1).LevelName, datas.get(1).LevelImg);
        itemView3.setView(context, datas.get(2).LevelName, datas.get(2).LevelImg);
        itemView4.setView(context, datas.get(7).LevelName, datas.get(7).LevelImg);
        itemView6.setView(context, datas.get(3).LevelName, datas.get(3).LevelImg);
        itemView7.setView(context, datas.get(6).LevelName, datas.get(6).LevelImg);
        itemView8.setView(context, datas.get(5).LevelName, datas.get(5).LevelImg);
        itemView9.setView(context, datas.get(4).LevelName, datas.get(4).LevelImg);
        mediaPlayer = MediaPlayer.create(context, R.raw.youxigundong0);

    }

    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private void startMarquee() {

        isMarqueeRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMarqueeRunning) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (bg_1 != null && bg_2 != null) {
                                if (VISIBLE == bg_1.getVisibility()) {
                                    bg_1.setVisibility(GONE);
                                    bg_2.setVisibility(VISIBLE);
                                } else {
                                    bg_1.setVisibility(VISIBLE);
                                    bg_2.setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 10;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    if(getInterruptTime()==DEFAULT_SPEED){
                        mediaPlayer.start();
//                    }else {
//                        mediaPlayer = MediaPlayer.create(context, R.raw.youxigundong2);
//                        mediaPlayer.start();
//                    }
                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemViewArr.length) {
                                currentIndex = 0;
                            }

                            itemViewArr[preIndex].setFocus(false);
                            itemViewArr[currentIndex].setFocus(true);

                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;

                                if (!"网络错误".equals(bean.PrizeName)) {
                                    LotteryResultFragment fragment = new LotteryResultFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("data", bean);
                                    fragment.setArguments(bundle);
                                    fragment.show(fragmentManager, "");
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position, FragmentManager fragmentManager, TravelLuckyBean bean) {
        stayIndex = position;
        isTryToStop = true;
        this.fragmentManager = fragmentManager;
        this.bean = bean;
    }

    public void setDatas(Context context, List<TravelBean.PrizePlate> datas) {
        this.datas = datas;
        this.context = context;
        setupView();
    }
}