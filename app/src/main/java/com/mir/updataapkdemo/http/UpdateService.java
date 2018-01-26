package com.mir.updataapkdemo.http;

import com.mir.updataapkdemo.base.BaseBean;
import com.mir.updataapkdemo.bean.UpgradeBean;
import com.mir.updataapkdemo.constants.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public interface UpdateService {

    @Streaming //大文件时要加不然会OOM
    @GET(Api.UPGRADE)
    Call<BaseBean<UpgradeBean>> updateVersion();
}
