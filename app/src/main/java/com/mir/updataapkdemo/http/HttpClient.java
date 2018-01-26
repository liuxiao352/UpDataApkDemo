package com.mir.updataapkdemo.http;

import android.util.Log;

import com.mir.updataapkdemo.base.BaseApplication;
import com.mir.updataapkdemo.constants.Api;
import com.mir.updataapkdemo.constants.AppConfig;
import com.mir.updataapkdemo.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc 对Retrofit 封装
 */

public class HttpClient {

    private static HttpClient mInstance = null;
    private static final boolean mIsSetCache = false;
    private Retrofit mRetrofit = null;

    public static HttpClient getIns(){
        return getIns(null);
    }

    public static HttpClient getIns(String baseUrl){
        if (mInstance == null) {
            synchronized (HttpClient.class){
                if (mInstance == null){
                    mInstance = new HttpClient(baseUrl);
                }
            }
        }
        return mInstance;
    }

    public HttpClient(String baseUrl){
        if (baseUrl == null) {
            configRetrofit(Api.API_SERVICE);
        }else{
            configRetrofit(baseUrl);
        }
    }

    private void configRetrofit(String baseUrl) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (mIsSetCache) {
            File cacheFile = new File(AppConfig.CACHE_PATH);
            //设置缓存时间
            Cache cache = new Cache(cacheFile, AppConfig.CACHE_DATE);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    //检查网络是否可用
                    if (NetUtils.networkIsAvailable(BaseApplication.getContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (NetUtils.networkIsAvailable(BaseApplication.getContext())) {
                        int maxAge = 0;
                        //有网络 设置缓存超时时间0小时
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("nyn")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    }else{
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("nyn")
                                .build();
                    }
                    return response;
                }
            };
            builder.cache(cache).addInterceptor(cacheInterceptor);
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        //设置错误重连
        builder.retryOnConnectionFailure(true);
        //构建Retrofit
        OkHttpClient okHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 创建 api接口
     */
    public <T> T createService(Class<T> clz){
        Log.e("test", "mRetrofit : " + mRetrofit);
        return mRetrofit.create(clz);
    }

}
