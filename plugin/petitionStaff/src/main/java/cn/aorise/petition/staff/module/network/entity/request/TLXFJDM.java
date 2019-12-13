package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TLXFJDM {
    private String LX;
    private String FJDM;

    @Override
    public String toString() {
        return "TLXFJDM{" +
                "LX='" + LX + '\'' +
                ", FJDM='" + FJDM + '\'' +
                '}';
    }

    public String getLX() {
        return LX;
    }

    public void setLX(String LX) {
        this.LX = LX;
    }

    public String getFJDM() {
        return FJDM;
    }

    public void setFJDM(String FJDM) {
        this.FJDM = FJDM;
    }
}
