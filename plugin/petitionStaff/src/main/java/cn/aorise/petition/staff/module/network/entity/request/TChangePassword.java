package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/6.
 */

public class TChangePassword {
    private String YHM;
    private String JMM;
    private String XMM;

    @Override
    public String toString() {
        return "TChangePassword{" +
                "YHM='" + YHM + '\'' +
                ", JMM='" + JMM + '\'' +
                ", XMM='" + XMM + '\'' +
                '}';
    }

    public String getYHM() {
        return YHM;
    }

    public void setYHM(String YHM) {
        this.YHM = YHM;
    }

    public String getJMM() {
        return JMM;
    }

    public void setJMM(String JMM) {
        this.JMM = JMM;
    }

    public String getXMM() {
        return XMM;
    }

    public void setXMM(String XMM) {
        this.XMM = XMM;
    }
}
