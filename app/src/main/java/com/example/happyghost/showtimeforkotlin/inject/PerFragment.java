package com.example.happyghost.showtimeforkotlin.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by long on 2016/8/23.
 * 单例标识
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
/*  kotlin版
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class FragmentScope
 */