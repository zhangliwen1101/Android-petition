package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/16.
 */

public class RWorkWarningList {
    private String BH;
    private String LFRQ;
    private String NR;
    private String ZJHM;
    private String XBSJ;
    private String ID;

    @Override
    public String toString() {
        return "RWorkWarningList{" +
                "BH='" + BH + '\'' +
                ", LFRQ='" + LFRQ + '\'' +
                ", NR='" + NR + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", XBSJ='" + XBSJ + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getXBSJ() {
        return XBSJ;
    }

    public void setXBSJ(String XBSJ) {
        this.XBSJ = XBSJ;
    }

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getLFRQ() {
        return LFRQ;
    }

    public void setLFRQ(String LFRQ) {
        this.LFRQ = LFRQ;
    }

    public String getNR() {
        return NR;
    }

    public void setNR(String NR) {
        this.NR = NR;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }
}
