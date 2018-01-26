package com.mir.updataapkdemo.bean;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/26
 * @desc
 */

public class UpgradeBean {

    /**
     * version : 版本号
     * name : 名称
     * describe : 升级描述
     * filepath : 安卓包下载地址
     * force : 是否强制升级   0：否  1：是
     */

    private String version;
    private String name;
    private String describe;
    private String filepath;
    private String force;
    private int versioncode;

    public int getVersionCode() {
        return versioncode;
    }

    public void setVersionCode(int versionCode) {
        this.versioncode = versionCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

}
