package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/14.
 */

public class RWorkWarning {
    private String LF_LJ;
    private String LX_LJ;
    private String LD_LJ;
    private String WX_LJ;
    private String LF_YQ;
    private String LX_YQ;
    private String LD_YQ;
    private String WX_YQ;

    @Override
    public String toString() {
        return "RWorkWarning{" +
                "LF_LJ='" + LF_LJ + '\'' +
                ", LX_LJ='" + LX_LJ + '\'' +
                ", LD_LJ='" + LD_LJ + '\'' +
                ", WX_LJ='" + WX_LJ + '\'' +
                ", LF_YQ='" + LF_YQ + '\'' +
                ", LX_YQ='" + LX_YQ + '\'' +
                ", LD_YQ='" + LD_YQ + '\'' +
                ", WX_YQ='" + WX_YQ + '\'' +
                '}';
    }

    public String getLF_LJ() {
        return LF_LJ;
    }

    public void setLF_LJ(String LF_LJ) {
        this.LF_LJ = LF_LJ;
    }

    public String getLX_LJ() {
        return LX_LJ;
    }

    public void setLX_LJ(String LX_LJ) {
        this.LX_LJ = LX_LJ;
    }

    public String getLD_LJ() {
        return LD_LJ;
    }

    public void setLD_LJ(String LD_LJ) {
        this.LD_LJ = LD_LJ;
    }

    public String getWX_LJ() {
        return WX_LJ;
    }

    public void setWX_LJ(String WX_LJ) {
        this.WX_LJ = WX_LJ;
    }

    public String getLF_YQ() {
        return LF_YQ;
    }

    public void setLF_YQ(String LF_YQ) {
        this.LF_YQ = LF_YQ;
    }

    public String getLX_YQ() {
        return LX_YQ;
    }

    public void setLX_YQ(String LX_YQ) {
        this.LX_YQ = LX_YQ;
    }

    public String getLD_YQ() {
        return LD_YQ;
    }

    public void setLD_YQ(String LD_YQ) {
        this.LD_YQ = LD_YQ;
    }

    public String getWX_YQ() {
        return WX_YQ;
    }

    public void setWX_YQ(String WX_YQ) {
        this.WX_YQ = WX_YQ;
    }
}
