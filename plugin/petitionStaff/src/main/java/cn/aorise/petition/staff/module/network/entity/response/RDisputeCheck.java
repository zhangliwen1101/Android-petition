package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/1.
 */

public class RDisputeCheck {
    private String JFLX;
    private String JFJB;
    private String JFNR;
    private String JFDZ;
    private String SFD;

    @Override
    public String toString() {
        return "RDisputeCheck{" +
                "JFLX='" + JFLX + '\'' +
                ", JFJB='" + JFJB + '\'' +
                ", JFNR='" + JFNR + '\'' +
                ", JFDZ='" + JFDZ + '\'' +
                ", SFD='" + SFD + '\'' +
                '}';
    }

    public String getJFLX() {
        return JFLX;
    }

    public void setJFLX(String JFLX) {
        this.JFLX = JFLX;
    }

    public String getJFJB() {
        return JFJB;
    }

    public void setJFJB(String JFJB) {
        this.JFJB = JFJB;
    }

    public String getJFNR() {
        return JFNR;
    }

    public void setJFNR(String JFNR) {
        this.JFNR = JFNR;
    }

    public String getJFDZ() {
        return JFDZ;
    }

    public void setJFDZ(String JFDZ) {
        this.JFDZ = JFDZ;
    }

    public String getSFD() {
        return SFD;
    }

    public void setSFD(String SFD) {
        this.SFD = SFD;
    }
}
