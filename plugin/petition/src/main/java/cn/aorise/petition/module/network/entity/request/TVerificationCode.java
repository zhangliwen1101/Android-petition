package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TVerificationCode {
    private String DHHM;

    @Override
    public String toString() {
        return "TVerificationCode{" +
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

    private String LX;


    public String getDHHM() {
        return DHHM;
    }

    public void setDHHM(String DHHM) {
        this.DHHM = DHHM;
    }
}
