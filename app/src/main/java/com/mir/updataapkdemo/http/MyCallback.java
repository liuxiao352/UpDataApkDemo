package com.mir.updataapkdemo.http;

import com.mir.updataapkdemo.R;
import com.mir.updataapkdemo.base.BaseBean;
import com.mir.updataapkdemo.utils.LogUtils;
import com.mir.updataapkdemo.utils.ToastUtils;
import com.mir.updataapkdemo.utils.UiUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public abstract class MyCallback<T extends BaseBean> implements Callback<T> {

    @Override
    public void onResponse(retrofit2.Call<T> call, Response<T> response) {
        //200请求成功
        int code = response.raw().code();
        if (code == 200) {
            //根据服务器规定 对状态码进行判断
            if (response.body().getStatus() == 0) {
                onSuccess(response);
            }else{
                ToastUtils.show(UiUtils.getContext(), response.body().getMsg());
                onFail(response.body().getMsg());
            }
        } else {
            onFailure(call, new RuntimeException("response error, detail=" + response.raw().toString()));
        }
    }

    @Override
    public void onFailure(retrofit2.Call<T> call, Throwable t) {
        LogUtils.e(TAG, "request failure:  \n" + call.request().toString());
        LogUtils.e(TAG, "request failure:  \n" + t.getMessage() + " ==== " + t.toString());

        // 对不同网络错误做不同处理
        if (t instanceof SocketTimeoutException) { // 连接超时
            ToastUtils.show(UiUtils.getContext(), UiUtils.getString(R.string.request_time_out_fail));
        } else if (t instanceof ConnectException) {// 网络连接错误
            ToastUtils.show(UiUtils.getContext(), UiUtils.getString(R.string.request_connect_fail));
        } else if (t instanceof UnknownHostException) {// DNS解析错误
            ToastUtils.show(UiUtils.getContext(), UiUtils.getString(R.string.request_unknown_host_fail));
        } else if (t instanceof IllegalStateException) {// 参数解析失败
            ToastUtils.show(UiUtils.getContext(), UiUtils.getString(R.string.request_illegal_state_fail));
        } else if (t instanceof RuntimeException) {// 运行时异常
            String message = t.getMessage();
            if(message.contains("404") || message.contains("505")){
                ToastUtils.show(UiUtils.getContext(), UiUtils.getString(R.string.request_server_busy_fail));
            }else{
                ToastUtils.show(UiUtils.getContext(), message);
            }
        }
        onFail(t.getMessage() + " ==== " + t.toString());
    }

    /**
     * 请求成功回调
     */
    public abstract void onSuccess(Response<T> response);

    /**
     * 请求失败回调
     */
    public abstract void onFail(String message);

}
