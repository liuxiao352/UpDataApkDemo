package com.mir.updataapkdemo.utils;

import android.content.Context;
import android.content.res.Resources;

import com.mir.updataapkdemo.base.BaseApplication;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public class UiUtils {

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        return BaseApplication.getContext();
    }

    /**
     * 获取资源操作类
     * @return
     */
    public static Resources getResources(){
        return getContext().getResources();
    }

    /**
     * 获取字符串资源
     * @param id 资源 id
     * @return 字符串
     */
    public static String getString(int id){
        return getResources().getString(id);
    }

}
