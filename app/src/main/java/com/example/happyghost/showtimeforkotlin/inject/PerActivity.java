package com.example.happyghost.showtimeforkotlin.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
/*  Kotlin版
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ActivityScope
 */