package com.example.who.githuborganizations.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import static com.example.who.githuborganizations.global.Constants.BASIC;
import static com.example.who.githuborganizations.global.Constants.LOGIN;
import static com.example.who.githuborganizations.global.Constants.PASSWORD;

/**
 * Created by who on 26.09.2017.
 */

public abstract class TokenUtils {
    public static String getMyToken(){
        String forBase64 = LOGIN + ":" + PASSWORD;
        byte[] data = forBase64.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).trim();
        return BASIC +" "+ base64;
    }
}
