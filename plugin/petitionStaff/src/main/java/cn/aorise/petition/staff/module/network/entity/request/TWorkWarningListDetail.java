package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/19.
 */

public class TWorkWarningListDetail {
    private String BH;
    private String ZJHM;

    @Override
    public String toString() {
        return "TWorkWarningListDetail{" +
                "BH='" + BH + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                '}';
    }

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }
}
