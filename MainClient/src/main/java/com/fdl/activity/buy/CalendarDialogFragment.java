package com.fdl.activity.buy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fdl.adapter.SuperMarkAdapter;
import com.fdl.wedgit.CalendarList;
import com.sg.cj.snh.R;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/1<p>
 * <p>changeTime：2019/4/1<p>
 * <p>version：1<p>
 */
public class CalendarDialogFragment extends DialogFragment {

    private CalendarList calendarList;
    private LinearLayout view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.Dialog_FS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = (LinearLayout) inflater.inflate(R.layout.dialog_calendar_layout, container, false);
        calendarList = view.findViewById(R.id.calendarList);
        return view;
    }

    @Override
    public void onStart() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = (int) display.getWidth();
        layoutParams.height = (int) (display.getHeight() * 0.8);
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setContentView(view);
        getDialog().getWindow().setWindowAnimations(R.style.MyDialogAlpha);
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
