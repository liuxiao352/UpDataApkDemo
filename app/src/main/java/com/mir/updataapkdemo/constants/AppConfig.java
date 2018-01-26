package com.mir.updataapkdemo.constants;

import android.os.Environment;

import java.io.File;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public class AppConfig {

    /** SD卡目录*/
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    /** 缓存目录的名字*/
    public static final String CACHE_NAME = "cache";

    /** 缓存目录路径*/
    public static final String CACHE_PATH = SDCARD_PATH.concat(CACHE_NAME.concat(File.separator));

    /** 缓存时间 一天总秒数*/
    public static final int CACHE_DATE = 60 * 60 * 23;
}
