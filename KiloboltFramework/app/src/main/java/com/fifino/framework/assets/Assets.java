package com.fifino.framework.assets;

import java.util.HashMap;

import android.graphics.Bitmap;

import com.kilobolt.framework.Image;
import com.kilobolt.framework.Sound;
import com.kilobolt.framework.implementation.AndroidImage;

public class Assets {
    public static String ASSETS_PATH = "assets";
    public static String IMAGES_PATH = "images";
    public static String SOUNDS_PATH = "sounds";
    public static HashMap<String, Image> images =  new HashMap<String, Image>();
    public static HashMap<String, Sound> sounds =  new HashMap<String, Sound>();
    public static HashMap<String, Image> addImage(String key, Image value){
        Assets.images.put(key, value);
        return images;
    }
    public static HashMap<String, Sound> addSound(String key, Sound value){
        Assets.sounds.put(key, value);
        return sounds;
    }
    public static Image getImage(String key){
        return Assets.images.get(key);
    }
    public static Image getImage(String key, boolean isNew){
        Image currentImage = getImage(key);
        if(isNew){
            Bitmap bitmap = currentImage.getBitmap();
            AndroidImage newImage = new AndroidImage(Bitmap.createBitmap(bitmap), currentImage.getFormat());
            return newImage;
        }else{
            return currentImage;
        }
    }
    public static Sound getSound(String key){
        return Assets.sounds.get(key);
    }
}