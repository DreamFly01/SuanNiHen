package com.sg.cj.common.base.adapter;

/**
 * author : ${CHENJIE}
 * created at  16/10/17 14:14
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
        private final SparseArray<View> mViews;
        private static int mPosition;
        private View mConvertView;
        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
                mPosition = position;
                this.mViews = new SparseArray<View>();
                mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
                mConvertView.setTag(this);
        }

        private ViewHolder(Context context, ViewGroup parent, View convertView, int position) {
                mPosition = position;
                this.mViews = new SparseArray<View>();
                mConvertView = convertView;
                mConvertView.setTag(this);
        }

        /**
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
                if (convertView == null) {
                        return new ViewHolder(context, parent, layoutId, position);
                }
                mPosition=position;
                return (ViewHolder) convertView.getTag();
        }

        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int position) {
                if (convertView == null) {
                        return null;
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                if (holder == null) {
                        holder = new ViewHolder(context, parent, convertView, position);
                }
                return holder;
        }

        private static void init(int position) {

        }

        public View getConvertView() {
                return mConvertView;
        }

        /**
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
                View view = mViews.get(viewId);
                if (view == null) {
                        view = mConvertView.findViewById(viewId);
                        mViews.put(viewId, view);
                }
                return (T) view;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
                View view = getView(viewId);
                view.setOnClickListener(onClickListener);
                return this;
        }
        public ViewHolder setText(int viewId, String text) {
                TextView view = getView(viewId);
                view.setText(text);
                return this;
        }
        public ViewHolder setEditText(int viewId, String text,boolean enable) {
                EditText view = getView(viewId);
                view.setText(text);
                view.setEnabled(enable);
                return this;
        }



        /**
         * 传颜色值，不能直接传颜色ID
         * @param viewId
         * @param textColor
         * @return
         */
        public ViewHolder setTextColor(int viewId, int textColor) {
                TextView view = getView(viewId);
                view.setTextColor(textColor);
                return this;
        }

        public ViewHolder setTextLeftDrawable(int viewId, Drawable textLeftDrawable) {
                TextView view = getView(viewId);
                if (textLeftDrawable != null) {
                        textLeftDrawable.setBounds(0, 0, textLeftDrawable.getMinimumWidth(), textLeftDrawable.getMinimumHeight());
                }
                view.setCompoundDrawables(textLeftDrawable, null, null, null);
                return this;
        }

        public ViewHolder setTextLeftDrawable(int viewId, Drawable textLeftDrawable, int width, int height) {
                TextView view = getView(viewId);
                if (textLeftDrawable != null) {
                        textLeftDrawable.setBounds(0, 0, width, height);
                }
                view.setCompoundDrawables(textLeftDrawable, null, null, null);
                return this;
        }


        public ViewHolder setImageResource(int viewId, int drawableId) {
                ImageView view = getView(viewId);
                view.setImageResource(drawableId);
                return this;
        }


        public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
                ImageView view = getView(viewId);
                view.setImageBitmap(bm);
                return this;
        }

        public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
                ImageView view = getView(viewId);
                view.setImageDrawable(drawable);

                return this;
        }

        public ViewHolder setVisibility(int viewId, int visible) {
                View view = getView(viewId);
                view.setVisibility(visible);
                return this;
        }

        public int getPosition() {
                return mPosition;
        }


}
