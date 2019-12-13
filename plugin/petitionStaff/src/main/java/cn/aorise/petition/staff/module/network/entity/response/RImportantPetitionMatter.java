package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/1.
 */

public class RImportantPetitionMatter {
    private String BH;
    private String XM;
    private String LX;
    private String JB;
    private String SJ;

    @Override
    public String toString() {
        return "RImportantPetitionMatter{" +
                "BH='" + BH + '\'' +
                ", XM='" + XM + '\'' +
                ", LX='" + LX + '\'' +
                ", JB='" + JB + '\'' +
                ", SJ='" + SJ + '\'' +
                '}';
    }

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getLX() {
        return LX;
    }

    public void setLX(String LX) {
        this.LX = LX;
    }

    public String getJB() {
        return JB;
    }

    public void setJB(String JB) {
        this.JB = JB;
    }

    public String getSJ() {
        return SJ;
    }

    public void setSJ(String SJ) {
        this.SJ = SJ;
    }
}
