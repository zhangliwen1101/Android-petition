package cn.aorise.petition.staff.ui.bean;

/**
 * Created by Administrator on 2017/6/19.
 */

public class ContactpeopleInfo {
    private String XM;
    private String ZJHM;
    private String DZ;

    @Override
    public String toString() {
        return "ContactpeopleInfo{" +
                "XM='" + XM + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", DZ='" + DZ + '\'' +
                '}';
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }

    public String getDZ() {
        return DZ;
    }

    public void setDZ(String DZ) {
        this.DZ = DZ;
    }
}
