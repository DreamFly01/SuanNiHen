package com.sg.cj.snh.di.qualifier;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * author : ${CHENJIE}
 * created at  2018/10/26 15:16
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface HttpUrl {

}