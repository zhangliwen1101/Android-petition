package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/7/11.
 */

public class TBH {
    private String BH;

    @Override
    public String toString() {
        return "TBH{" +
                "BH='" + BH + '\'' +
                '}';
    }

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }
}
