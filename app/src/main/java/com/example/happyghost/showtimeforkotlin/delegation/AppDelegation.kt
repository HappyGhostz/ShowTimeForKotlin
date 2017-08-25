package com.example.happyghost.showtimeforkotlin.delegation

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Zhao Chenping
 * @creat 2017/8/24.
 * @description
 */
public class AppDelegation<T>() :ReadWriteProperty<Any?,T> {

    var vaule : T?=null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return vaule?:throw IllegalStateException("&{decs.name}"+"not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.vaule=if(this.vaule==null) vaule
        else throw IllegalStateException("&{decs.name} is already initialized")
    }


}