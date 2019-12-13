package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/11.
 */

public class TQueryEvaluateDetail {
    private String ID;

    @Override
    public String toString() {
        return "TQueryEvaluateDetail{" +
                "BH='" + ID + '\'' +
                '}';
    }

    public String getBH() {
        return ID;
    }

    public void setBH(String BH) {
        this.ID = BH;
    }
}
