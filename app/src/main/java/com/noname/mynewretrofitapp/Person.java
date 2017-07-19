package com.noname.mynewretrofitapp;

import java.net.URL;

class Person {
    String name;
    String age;
    int photoId;
    String photoLink;

    Person(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }
    Person(String name, String age, String photoLink) {
        this.name = name;
        this.age = age;
        this.photoLink = photoLink;
        photoId = -1;
    }
}