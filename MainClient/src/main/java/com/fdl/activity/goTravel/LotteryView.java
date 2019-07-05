package com.fdl.activity.goTravel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.sg.cj.common.base.utils.SgLog;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/25<p>
 * <p>changeTime：2019/4/25<p>
 * <p>version：1<p>
 */
public class LotteryView extends SurfaceView implements Callback {

    /**
     * holder
     */
    private SurfaceHolder mHolder;


    private List<Prize> prizes;
    private boolean flags;

    private int lottery = 6; //设置中奖号码

    private int current = 2; //抽奖开始的位置

    private int count = 0; //旋转次数累计

    private int countDown; //倒计次数，快速旋转完成后，需要倒计多少次循环才停止

    private int transfer = 0xffff0000;//中奖背景

    private int MAX = 50; //最大旋转次数

    private OnTransferWinningListener listener;

    public void setOnTransferWinningListener(OnTransferWinningListener listener) {
        this.listener = listener;
    }

    public interface OnTransferWinningListener {
        /**
         * 中奖回调
         *
         * @param position
         */
        void onWinning(int position);
    }


    /**
     * 设置中奖号码
     *
     * @param lottery
     */
    public void setLottery(int lottery) {
        if (prizes != null && Math.round(prizes.size() / 2) == 0) {
            throw new RuntimeException("开始抽奖按钮不能设置为中奖位置！");
        }
        this.lottery = lottery;
    }

    /**
     * 设置中奖颜色
     *
     * @param transfer
     */
    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    /**
     * 设置奖品集合
     *
     * @param prizes
     */
    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event);
        return super.onTouchEvent(event);
    }

    /**
     * 触摸
     *
     * @param event
     */
    public void handleTouch(MotionEvent event) {
        Point touchPoint = new Point((int) event.getX() - getLeft(), (int) event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Prize prize = prizes.get(Math.round(prizes.size()) / 2);
                if (prize.isClick(touchPoint, getMeasuredWidth())) {
                    if (!flags) {
                        setStartFlags(true);
                        prize.click();
                    }
                }
                break;
            default:
                break;
        }
    }

    private class SurfaceRunnable implements Runnable {
        @Override
        public void run() {
            while (flags) {
                Canvas canvas = null;
                try {
                    canvas = mHolder.lockCanvas();

                    drawBg(canvas);

                    drawTransfer(canvas);

                    drawPrize(canvas);

                    controllerTransfer();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    //绘制所有奖品背景
    private void drawBg(Canvas canvas) {
        canvas.drawColor(Color.WHITE, Mode.CLEAR);
//        int width = getMeasuredWidth() / 3;
        int width = getMeasuredHeight()/3;
        SgLog.d("width:" + getMeasuredHeight());
        int x1 = 0;
        int y1 = 0;

        int x2 = 0;
        int y2 = 0;

        int len = (int) Math.sqrt(prizes.size());

        for (int x = 0; x < len * len; x++) {


            Prize prize = prizes.get(x);

            int index = x;
            x1 = getPaddingLeft() + width * (Math.abs(index) % len);
            y1 = getPaddingTop() + width * (index / len);

            x2 = x1 + width;
            y2 = y1 + width;
            Rect rect = null;
            if (x == 0) {
                rect = new Rect(0, 0, 363, 239);
            }else if(x==1){
                rect = new Rect(363, 0, 602, 239);
            }else if(x==2){
                rect = new Rect(602, 0, 841,239);
            } else if(x == 4){
                rect = new Rect(239, 239, 602, 478);
            } else if(x==3){
                rect = new Rect(0, 239, 239, 478);
            }else if(x==5){
                rect = new Rect(602, 239, 841, 478);
            }else if(x==6){
                rect = new Rect(0, 478, 239, 717);
            }
            else if(x==7){
                rect = new Rect(239, 478, 478, 717);
            }
            else if(x==8){
                rect = new Rect(239, 478, 841, 717);
            }
            Paint paint = new Paint();
            paint.setColor(prize.getBgColor());
            canvas.drawRect(rect, paint);
        }
    }

    //绘制旋转的奖品背景
    private void drawTransfer(Canvas canvas) {
        int width = getMeasuredWidth() / 3;
        int x1;
        int y1;

        int x2;
        int y2;
        int len = (int) Math.sqrt(prizes.size());
        current = next(current, len);
        x1 = getPaddingLeft() + width * (Math.abs(current) % len);
        y1 = getPaddingTop() + width * ((current) / len);

        x2 = x1 + width;
        y2 = y1 + width;

        Rect rect = new Rect(x1, y1, x2, y2);
        Paint paint = new Paint();
        paint.setColor(transfer);
        canvas.drawRect(rect, paint);
    }

    //控制旋转的速度
    private void controllerTransfer() {
        if (count > MAX) {
            countDown++;
            SystemClock.sleep(count * 5);
        } else {
            SystemClock.sleep(count * 2);
        }

        count++;
        if (countDown > 2) {
            if (lottery == current) {
                countDown = 0;
                count = 0;
                setStartFlags(false);
                if (listener != null) {
                    //切换到主线程中运行
                    post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onWinning(current);
                        }
                    });

                }
            }
        }
    }

    public void setStartFlags(boolean flags) {
        this.flags = flags;
    }

    //绘制奖品背景
    private void drawPrize(Canvas canvas) {
        int width = getMeasuredWidth() / 3;
        int x1 = 0;
        int y1 = 0;

        int x2 = 0;
        int y2 = 0;

        int len = (int) Math.sqrt(prizes.size());

        for (int x = 0; x < len * len; x++) {

            Prize prize = prizes.get(x);

            int index = x;
            x1 = getPaddingLeft() + width * (Math.abs(index) % len);
            y1 = getPaddingTop() + width * (index / len);

            x2 = x1 + width;
            y2 = y1 + width;

            Rect rect = null;
            if (x == 0) {
                rect = new Rect(x1+ width / 30, y1+ width / 30, x2+80- width / 30, y2- width / 30);
            }else if(x==1){
                rect = new Rect(x1+80+ width / 30, y1+ width / 30, x2+40- width / 30, y2- width / 30);
            }else if(x==2){
                rect = new Rect(x1+40+ width / 30, y1+ width / 30, x2+40- width / 30, y2- width / 30);
            } else if(x == 4){
                rect = new Rect(x1, y1, x2, y2);
            } else if(x==3||x==5){
                rect = new Rect(x1, y1, x2, y2);
            } else if(x==8){
                rect = new Rect(x1, y1, x2, y2);
            }else {
                rect = new Rect(x1+10, y1, x2-10, y2);
            }
//            if (x == 0 || x == 8) {
//                rect = new Rect(x1 + width / 30, y1 + width / 30, x2 - width / 30+40, y2 - width / 30);
//            } else if(x==3||x==4||x==5){
//                rect = new Rect(x1 + width / 30, y1 + width / 30, x2 - width / 30, y2 - width / 30);
//            } else {
//                rect = new Rect(x1 + width / 30-10, y1 + width / 30, x2 - width / 30+10, y2 - width / 30);
//            }
//            Rect rect = new Rect(x1 + width / 30, y1 + width / 30, x2 - width / 30, y2 - width / 30);
            prize.setRect(rect);
            canvas.drawBitmap(prize.getIcon(), null, rect, null);
        }
    }


    public void start() {
        setLottery(lottery);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new SurfaceRunnable());
    }

    //获取随机中奖数，实际开发中一般中奖号码是服务器告诉我们的
    private int getRandom() {
        Random r = new Random();
        int nextInt = r.nextInt(prizes.size());
        if (nextInt % (Math.round(prizes.size() / 2)) == 0) {
            //随机号码等于中间开始位置，需要继续摇随机号
            return getRandom();
        }
        return nextInt;
    }

    //下一步
    public int next(int current, int len) {
        if (current + 1 < len) {
            return ++current;
        }

        if ((current + 1) % len == 0 & current < len * len - 1) {
            return current += len;
        }

        if (current % len == 0) {
            return current -= len;
        }

        if (current < len * len) {
            return --current;
        }

        return current;
    }


    public LotteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    public LotteryView(Context context) {
        this(context, null);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = mHolder.lockCanvas();
            drawBg(canvas);
            drawPrize(canvas);
            Prize prize = prizes.get(Math.round(prizes.size() / 2));
            prize.setListener(new Prize.OnClickListener() {

                @Override
                public void click() {
                    start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null)
                mHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        setStartFlags(false);
    }

    /**
     * 重新测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(width, width);
    }

}
