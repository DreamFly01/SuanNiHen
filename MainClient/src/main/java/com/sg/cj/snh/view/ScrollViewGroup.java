package com.sg.cj.snh.view;


import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Scroller;

/**
 * author : ${CHENJIE}
 * created at  2018/12/16 20:37
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class ScrollViewGroup extends ViewGroup {



  private int lastX;
  private int lastY;
  private int currentIndex = 0; //当前子元素
  private int childWidth = 0;
  private Scroller scroller;
  private VelocityTracker tracker;    //增加速度检测,如果速度比较快的话,就算没有滑动超过一半的屏幕也可以
  private int lastInterceptX=0;
  private int lastInterceptY=0;

  //屏幕宽度
  private int screenWidth;

  public ScrollViewGroup(Context context) {
    super(context);
    init();
  }
  public ScrollViewGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }
  public ScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void init() {

    //获取屏幕宽度
    WindowManager manager = (WindowManager) getContext()
        .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    screenWidth = outMetrics.widthPixels;
    //

    scroller = new Scroller(getContext());
    tracker = VelocityTracker.obtain();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    boolean intercept = false;
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        intercept = false;
        //如果scroller没有执行完毕,则打断
        if (!scroller.isFinished()) {
          scroller.abortAnimation();
        }
        break;
      case MotionEvent.ACTION_MOVE:
        int deltaX = x - lastInterceptX;
        int deltaY = y - lastInterceptY;
        //水平方向距离长  MOVE中返回true一次,后续的MOVE和UP都不会收到此请求
        if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
          intercept = true;
        } else {
          intercept = false;
        }
        break;
      case MotionEvent.ACTION_UP:
        intercept = false;
        break;
    }
    lastX = x;
    lastY = y;
    lastInterceptX = x;
    lastInterceptY = y;
    return intercept;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    tracker.addMovement(event);
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (!scroller.isFinished()) {
          scroller.abortAnimation();
        }
        break;
      case MotionEvent.ACTION_MOVE:
        //跟随手指滑动
        int deltaX = x - lastX;
        scrollBy(-deltaX, 0);
        break;
      //释放手指以后开始自动滑动到目标位置
      case MotionEvent.ACTION_UP:
        //相对于当前View滑动的距离,正为向左,负为向右
        int distance = getScrollX() - currentIndex * childWidth;

        //必须滑动的距离要大于1/2个宽度,否则不会切换到其他页面
        if (Math.abs(distance) > childWidth / 2) {
          if (distance > 0) {
            currentIndex++;
          } else {
            currentIndex--;
          }
        } else {
          tracker.computeCurrentVelocity(1000);
          float xV = tracker.getXVelocity();
          if (Math.abs(xV) > 50) {
            if (xV > 0) {
              currentIndex--;
            } else {
              currentIndex++;
            }
          }
        }
        if(currentIndex<0){
          currentIndex=0;
        }else if(currentIndex>1){
          currentIndex=1;
        }
        smoothScrollTo(currentIndex * childWidth, 0);
        tracker.clear();
        break;
      default:
        break;
    }
    lastX = x;
    lastY = y;
    return true;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);
    int childWidth = screenWidth / 5;
    final int count = getChildCount();
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      childHeight = child.getMeasuredHeight();
      int childWidthSpec = getChildMeasureSpec(
          MeasureSpec
              .makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
          0, childWidth);
      child.measure(childWidthSpec, child.getMeasuredHeight());
    }
    setMeasuredDimension(width, height);
  }



  @Override
  public void computeScroll() {
    super.computeScroll();
    if (scroller.computeScrollOffset()) {
      scrollTo(scroller.getCurrX(), scroller.getCurrY());
      postInvalidate();
    }
  }
  public void smoothScrollTo(int destX, int destY) {
    scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
    invalidate();
  }

  int totalPage;
  // item的宽度
  //private int childWidth = 0;
  // item的高度
  private int childHeight = 0;

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int childCount = getChildCount();

    totalPage = (int) Math.ceil(childCount * 1.0 / (5 * 2));

    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      if (childView.getVisibility() != View.GONE) {
        childWidth = childView.getMeasuredWidth();
        childHeight = childView.getMeasuredHeight();
        int row = i % 2;
        int col = i / 2;
        // int page = i / itemPerPageMax;
        int left = col
            * childWidth;
        int top = row * childHeight;
        childView.layout(left, top, left + childWidth,
            top + childHeight);
      }
    }
  }

  // 绘制Container所有item
  public void refreView() {
    removeAllViews();


    for (int i = 0; i < mAdapter.getCount(); i++) {
      this.addView(getView(i));
    }

//    if (pageChangedListener != null)
//      pageChangedListener.onPage(mCurScreen, totalPage);

    postInvalidate();
  }

  public void setAdapter(@NonNull BaseAdapter adapter) {
    if (adapter == null) throw new NullPointerException("adapter null");
    mAdapter = adapter;
    mAdapter.registerDataSetObserver(new DataSetObserver() {
      @Override
      public void onChanged() {
        refreView();
        super.onChanged();
      }

      @Override
      public void onInvalidated() {
        refreView();
        super.onInvalidated();
      }

    });
    refreView();
  }

  // 数据适配器
  private BaseAdapter mAdapter;

  // 获取特定position下的item View
  private View getView(final int position) {
    View view = null;
    if (mAdapter != null) {
      view = mAdapter.getView(position, getChildAt(position), this);
      view.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {

          if (null != onItemClickListener) {
            onItemClickListener.onItemClick(position);
          }
        }
      });
    }
    return view;
  }


  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private OnItemClickListener onItemClickListener;

  public interface OnItemClickListener {
    void onItemClick(int position);
  }


}
