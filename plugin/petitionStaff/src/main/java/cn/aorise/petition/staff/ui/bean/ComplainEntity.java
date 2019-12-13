package cn.aorise.petition.staff.ui.bean;

/**
 * Created by Administrator on 2017/6/1.
 */

public class ComplainEntity {
    private String XM;
    private String DHHM;
    private String DQ;
    private String XXDZ;
    private String ZJLX;
    private String ZJHM;
    private String CSRQ;
    private String XB;
    private String MZ;
    private String CYZK;
    private String ZZMM;
    private String ZPID;

    @Override
    public String toString() {
        return "ComplainEntity{" +
                "XM='" + XM + '\'' +
                ", DHHM='" + DHHM + '\'' +
                ", DQ='" + DQ + '\'' +
                ", XXDZ='" + XXDZ + '\'' +
                ", ZJLX='" + ZJLX + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", CSRQ='" + CSRQ + '\'' +
                ", XB='" + XB + '\'' +
                ", MZ='" + MZ + '\'' +
                ", CYZK='" + CYZK + '\'' +
                ", ZZMM='" + ZZMM + '\'' +
                ", ZPID='" + ZPID + '\'' +
                '}';
    }

    public String getZPID() {
        return ZPID;
    }

    public void setZPID(String ZPID) {
        this.ZPID = ZPID;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getDHHM() {
        return DHHM;
    }

    public void setDHHM(String DHHM) {
        this.DHHM = DHHM;
    }

    public String getDQ() {
        return DQ;
    }

    public void setDQ(String DQ) {
        this.DQ = DQ;
    }

    public String getXXDZ() {
        return XXDZ;
    }

    public void setXXDZ(String XXDZ) {
        this.XXDZ = XXDZ;
    }

    public String getZJLX() {
        return ZJLX;
    }

    public void setZJLX(String ZJLX) {
        this.ZJLX = ZJLX;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }

    public String getCSRQ() {
        return CSRQ;
    }

    public void setCSRQ(String CSRQ) {
        this.CSRQ = CSRQ;
    }

    public String getXB() {
        return XB;
    }

    public void setXB(String XB) {
        this.XB = XB;
    }

    public String getMZ() {
        return MZ;
    }

    public void setMZ(String MZ) {
        this.MZ = MZ;
    }

    public String getCYZK() {
        return CYZK;
    }

    public void setCYZK(String CYZK) {
        this.CYZK = CYZK;
    }

    public String getZZMM() {
        return ZZMM;
    }

    public void setZZMM(String ZZMM) {
        this.ZZMM = ZZMM;
    }
}
