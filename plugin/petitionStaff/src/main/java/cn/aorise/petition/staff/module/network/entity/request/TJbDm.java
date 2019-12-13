package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/7.
 */

public class TJbDm {
    private String JB;
    private String DM;

    @Override
    public String toString() {
        return "TJbDm{" +
                "JB='" + JB + '\'' +
                ", DM='" + DM + '\'' +
                '}';
    }

    public String getJB() {
        return JB;
    }

    public void setJB(String JB) {
        this.JB = JB;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }
}
