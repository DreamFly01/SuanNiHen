package com.sg.cj.snh.view.citypicker.adapter;


import com.sg.cj.snh.view.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
