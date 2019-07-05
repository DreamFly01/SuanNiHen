package com.sg.cj.snh.view.citypicker.adapter;


import com.sg.cj.snh.view.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
