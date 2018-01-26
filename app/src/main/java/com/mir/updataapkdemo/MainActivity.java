package com.mir.updataapkdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mir.updataapkdemo.base.BaseBean;
import com.mir.updataapkdemo.bean.UpgradeBean;
import com.mir.updataapkdemo.http.HttpClient;
import com.mir.updataapkdemo.http.MyCallback;
import com.mir.updataapkdemo.http.UpdateService;
import com.mir.updataapkdemo.utils.DownloadUtils;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_down_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVersion();
            }
        });
    }

    private void updateVersion() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("检查新版本");
        progressDialog.show();
        HttpClient.getIns().createService(UpdateService.class)
                .updateVersion()
                .enqueue(new MyCallback<BaseBean<UpgradeBean>>() {
                    @Override
                    public void onSuccess(Response<BaseBean<UpgradeBean>> response) {
                        progressDialog.dismiss();
                        UpgradeBean data = response.body().getData();
                        String version = data.getVersion();
                        String filepath = data.getFilepath();
                        String describe = data.getDescribe();
                        showDialog(version, describe, filepath);
                    }

                    @Override
                    public void onFail(String message) {
                        progressDialog.dismiss();
                    }
                });
    }


    private void showDialog(String version, final String desc, final String filepath){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本 V" + version);
        builder.setMessage(desc);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownloadUtils downloadUtils = new DownloadUtils(MainActivity.this);
                downloadUtils.downloadAPK(filepath, "YRHX.apk");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
