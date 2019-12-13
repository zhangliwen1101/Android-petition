package cn.aorise.petition.staff.ui.bean;

/**
 * Created by Administrator on 2017/5/27.
 */

public class StaffInfo {
    private String XM;
    private String DHHM;
    private String ZW;
    private String DZ;

    @Override
    public String toString() {
        return "StaffInfo{" +
                "XM='" + XM + '\'' +
                ", DHHM='" + DHHM + '\'' +
                ", ZW='" + ZW + '\'' +
                ", DZ='" + DZ + '\'' +
                '}';
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

    public String getZW() {
        return ZW;
    }

    public void setZW(String ZW) {
        this.ZW = ZW;
    }

    public String getDZ() {
        return DZ;
    }

    public void setDZ(String DZ) {
        this.DZ = DZ;
    }
}
