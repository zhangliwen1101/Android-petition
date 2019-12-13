package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/31.
 */

public class TDhhm {
    private String DHHM;
    private String LX;

    @Override
    public String toString() {
        return "TDhhm{" +
                "DHHM='" + DHHM + '\'' +
                ", LX='" + LX + '\'' +
                '}';
    }

    public String getLX() {
        return LX;
    }

    public void setLX(String LX) {
        this.LX = LX;
    }

    public String getDHHM() {
        return DHHM;
    }

    public void setDHHM(String DHHM) {
        this.DHHM = DHHM;
    }
}
