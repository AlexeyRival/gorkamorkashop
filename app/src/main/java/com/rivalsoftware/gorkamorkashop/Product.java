package com.rivalsoftware.gorkamorkashop;

import static androidx.core.math.MathUtils.clamp;
import static com.rivalsoftware.gorkamorkashop.Utility.BitMapToString;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

public class Product implements Serializable {
    public String name;
    public String picture;
    public int uid;
    public Product(String name, String picture, int uid) {
        this.name = name;
        this.picture = picture;
        this.uid=uid;
    }
    public Product()
    {

    }
}
