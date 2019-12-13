package cn.aorise.petition.staff.ui.bean;

/**
 * Created by Administrator on 2017/6/1.
 */

public class PeopleInfo {
    private String XM;
    private String ZJHM;
    private String DQ;

    @Override
    public String toString() {
        return "PeopleInfo{" +
                "XM='" + XM + '\'' +
                ", ZJHM='" + ZJHM + '\'' +
                ", DQ='" + DQ + '\'' +
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

    public String getDQ() {
        return DQ;
    }

    public void setDQ(String DQ) {
        this.DQ = DQ;
    }
}
