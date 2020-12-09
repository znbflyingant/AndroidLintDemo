package com.kronos.sample;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class User implements Serializable{
    public String name;

    private void getName(){

    }

    @Keep
    public static class Student implements Serializable{

    }
}
