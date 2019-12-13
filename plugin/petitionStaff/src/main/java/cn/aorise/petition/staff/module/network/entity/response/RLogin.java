package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/5/25.
 */

public class RLogin {
    private String ZP;
    private String XM;
    private String XB;
    private String ZJLX;
    private String ZJHM;
    private String DHHM;
    private String CSRQ;
    private String BH;
    private String XXDZ;
    private String MZ;
    private String SSBM;
    private String ID;

    @Override
    public String toString() {
        return "RLogin{" +
                "ZP='" + ZP + '\'' +
                ", XM='" + XM + '\'' +
                ", XB='" + XB + '\'' +
                ", ZJLX='" + ZJLX + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", DHHM='" + DHHM + '\'' +
                ", CSRQ='" + CSRQ + '\'' +
                ", BH='" + BH + '\'' +
                ", XXDZ='" + XXDZ + '\'' +
                ", MZ='" + MZ + '\'' +
                ", SSBM='" + SSBM + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    public String getZP() {
        return ZP;
    }

    public void setZP(String ZP) {
        this.ZP = ZP;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getXB() {
        return XB;
    }

    public void setXB(String XB) {
        this.XB = XB;
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

    public String getDHHM() {
        return DHHM;
    }

    public void setDHHM(String DHHM) {
        this.DHHM = DHHM;
    }

    public String getCSRQ() {
        return CSRQ;
    }

    public void setCSRQ(String CSRQ) {
        this.CSRQ = CSRQ;
    }

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getXXDZ() {
        return XXDZ;
    }

    public void setXXDZ(String XXDZ) {
        this.XXDZ = XXDZ;
    }

    public String getMZ() {
        return MZ;
    }

    public void setMZ(String MZ) {
        this.MZ = MZ;
    }

    public String getSSBM() {
        return SSBM;
    }

    public void setSSBM(String SSBM) {
        this.SSBM = SSBM;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
