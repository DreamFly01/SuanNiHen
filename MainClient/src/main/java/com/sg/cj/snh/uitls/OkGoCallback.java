package com.sg.cj.snh.uitls;

import com.fdl.bean.BaseResultBean;
import com.fdl.bean.OkGoBaseBean;
import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 陈自强
 * @date 2019/7/30
 */
public class OkGoCallback<T> extends AbsCallback<OkGoBaseBean<T>> {

    private Gson gson;

    @Override
    public void onSuccess(Response response) {
        gson = new Gson();
    }


    @Override
    public OkGoBaseBean<T> convertResponse(okhttp3.Response response) {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Type ty = new ParameterizedTypeImpl(OkGoBaseBean.class, new Type[]{types[0]});
            OkGoBaseBean<T> baseBean = gson.fromJson(response.body().toString(), ty);
            return baseBean;
        }
        return null;
    }

//    private T stringTobean(String json) {
//        Type type = getClass().getGenericSuperclass();
//        if (type instanceof ParameterizedType) {
//            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
//            Type ty = new ParameterizedTypeImpl(T , new Type[]{types[0]});
//            T baseBean = gson.fromJson(json, ty);
//            return baseBean;
//        }
//        return null;
//    }


    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
