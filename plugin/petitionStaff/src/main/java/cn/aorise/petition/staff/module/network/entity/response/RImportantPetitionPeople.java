package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/5/31.
 */

public class RImportantPetitionPeople {
    private String XM;
    private String DHHM;
    private String ZJHM;
    private String DQ;
    private String XXDZ;

    @Override
    public String toString() {
        return "RImportantPetitionPeople{" +
                "XM='" + XM + '\'' +
                ", DHHM='" + DHHM + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", DQ='" + DQ + '\'' +
                ", XXDZ='" + XXDZ + '\'' +
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

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
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
}
