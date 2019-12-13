package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/19.
 */

public class RAnalyzeOrgan {
    /**
     * jigou : 安全生产监督管理局
     * laifang : 0
     * laixin : 0
     * laidian : 0
     * wangxin : 0
     */

    private String jigou;
    private int laifang;
    private int laixin;
    private int laidian;
    private int wangxin;

    public String getJigou() {
        return jigou;
    }

    public void setJigou(String jigou) {
        this.jigou = jigou;
    }

    public int getLaifang() {
        return laifang;
    }

    public void setLaifang(int laifang) {
        this.laifang = laifang;
    }

    public int getLaixin() {
        return laixin;
    }

    public void setLaixin(int laixin) {
        this.laixin = laixin;
    }

    public int getLaidian() {
        return laidian;
    }

    public void setLaidian(int laidian) {
        this.laidian = laidian;
    }

    public int getWangxin() {
        return wangxin;
    }

    public void setWangxin(int wangxin) {
        this.wangxin = wangxin;
    }
}
