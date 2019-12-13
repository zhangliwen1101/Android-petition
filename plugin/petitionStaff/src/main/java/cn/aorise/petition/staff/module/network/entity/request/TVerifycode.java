package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/6.
 */

public class TVerifycode {
    private String YZM;
    private String DHHM;

    @Override
    public String toString() {
        return "TVerifycode{" +
                "YZM='" + YZM + '\'' +
                ", DHHM='" + DHHM + '\'' +
                '}';
    }

    public String getYZM() {
        return YZM;
    }

    public void setYZM(String YZM) {
        this.YZM = YZM;
    }

    public String getDHHM() {
        return DHHM;
    }

    public void setDHHM(String DHHM) {
        this.DHHM = DHHM;
    }
}
