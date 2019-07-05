package com.sg.cj.snh.uitls;

import android.provider.DocumentsContract;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description:
 * @author : chenjie
 * creat at 2018/10/29 16:39
 */
public class XmlUtils {

    //xml转bean对象,其中beanRootNode是xml解析根节点，beanClazz是自定义的解析bean对象
    public static <T> T getBean(String src, String beanRootNode, Class<T> beanClazz) throws Exception {
        T t = null;
        ByteArrayInputStream is = new ByteArrayInputStream(src.getBytes());
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        //一个计数器
        int count = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                //如果是xml文件开始标签，则初始化一些数据
                case XmlPullParser.START_DOCUMENT:
                    //最后的结果
                    break;
                //开始标签
                case XmlPullParser.START_TAG:
                    //获得标签的名字
                    String tagName = parser.getName();
                    //如果内层的ListBean已经实例化出来的话
                    if (t != null) {
                        try {
                            //判断当前标签在没在ListBean的属性中
                            Field field = beanClazz.getField(tagName);
                            //如果ListBean中有当前标签
                            if (field != null) {
                                //计数器+1
                                count++;
                                //将取出来的值赋给ListBean中对应的属性
                                field.set(t, parser.nextText());
                            }
                        } catch (Exception e) {
                            //如果ListBean中没有当前标签，则会直接跳到这里，什么都不执行，然后再继续往下走

                        }
                        //如果外层的Bean已经实例化出来的话
                    }
                    //如果当前标签为我们传入的标签，说明Bean需要实例化出来了
                    if (tagName.equals(beanRootNode)) {
                        //将Bean实例化出来
                        t = beanClazz.newInstance();
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    //如果当前标签
                    if (beanRootNode.equalsIgnoreCase(parser.getName())) {
                        //将Bean保存到result中
                    }
                    break;
            }
            //移动到下一个标签
            eventType = parser.next();
        }
        //如果计数器为0说明没有解析到任何数据
        if (count == 0) {
            t = null;
        }

        return t;
    }

    //获取list<T>,,其中listItem是xml解析list分节点，beanClazz是自定义的解析bean list对象
    public static <T> ArrayList<T> getListBean(String src, String listItemNode, Class<T> beanClazz) throws Exception {
        //list  存放一堆item
        ArrayList<T> list = null;
        //ListBean
        T t = null;
        ByteArrayInputStream is = new ByteArrayInputStream(src.getBytes());
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        //一个计数器
        int count = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                //如果是xml文件开始标签，则初始化一些数据
                case XmlPullParser.START_DOCUMENT:
                    //最后的结果
                    list = new ArrayList<T>();
                    break;
                //开始标签
                case XmlPullParser.START_TAG:
                    //获得标签的名字
                    String tagName = parser.getName();
                    //如果内层的ListBean已经实例化出来的话
                    if (t != null) {
                        try {
                            //判断当前标签在没在ListBean的属性中
                            Field field = beanClazz.getField(tagName);
                            //如果ListBean中有当前标签
                            if (field != null) {
                                //计数器+1
                                count++;
                                //将取出来的值赋给ListBean中对应的属性
                                field.set(t, parser.nextText());
                            }
                        } catch (Exception e) {
                            //如果ListBean中没有当前标签，则会直接跳到这里，什么都不执行，然后再继续往下走

                        }
                        //如果外层的Bean已经实例化出来的话
                    }
                    //如果当前标签为我们传入的标签，说明Bean需要实例化出来了
                    if (tagName.equals(listItemNode)) {
                        //将Bean实例化出来
                        t = beanClazz.newInstance();
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    //如果当前标签
                    if (listItemNode.equalsIgnoreCase(parser.getName())) {
                        //如果ListBean不为空
                        if (t != null) {
                            //保存到list中，同时也保存到了result中，因为list已经是保存在result中了，
                            //只不过刚才没有值，现在有值了
                            list.add(t);
                            //并且把ListBean置空，因为后续还有好多个item
                            t = null;
                        }
                    }
                    break;
            }
            //移动到下一个标签
            eventType = parser.next();
        }
        //如果计数器为0说明没有解析到任何数据
        if (count == 0) {
            t = null;
        }
        return list;
    }










}
