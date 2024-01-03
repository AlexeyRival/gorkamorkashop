package com.rivalsoftware.gorkamorkashop;

import static androidx.core.math.MathUtils.clamp;
import static com.rivalsoftware.gorkamorkashop.Utility.BitMapToString;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

public class ProductFactory {
    public static Product GenerateProduct(int uid, Random rand) {
        Bitmap b = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_4444);
        int[] pixels = new int[64 * 64];
        int x, y;
        int rr, gg, bb;
        for (int i = 0; i < pixels.length; ++i) {
            x = i % 64;
            y = i / 64;
            int power = Math.abs(x - 32) + Math.abs(y - 32);
            int rmax = clamp(Math.abs(255 - x * 3 + uid * 32)%255, 1, 255);
            int gmax = clamp(Math.abs(255 - y * 3 - uid * 32)%255, 1, 255);
            rr = (int) (rmax / (power / 32f));
            gg = (int) (gmax / (power / 32f));
            bb = (int) ((255 - power) / (power / 32f));
            pixels[i] = Color.rgb(rr, gg, bb);
        }
        b.setPixels(pixels, 0, 64, 0, 0, 64, 64);
        return new Product("Чоппа", BitMapToString(b), uid);
    }
}
