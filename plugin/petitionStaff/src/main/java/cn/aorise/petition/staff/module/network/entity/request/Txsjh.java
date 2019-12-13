package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/6.
 */

public class Txsjh {
    private String YHM;
    private String XSJH;

    @Override
    public String toString() {
        return "Txsjh{" +
                "YHM='" + YHM + '\'' +
                ", XSJH='" + XSJH + '\'' +
                '}';
    }

    public String getYHM() {
        return YHM;
    }

    public void setYHM(String YHM) {
        this.YHM = YHM;
    }

    public String getXSJH() {
        return XSJH;
    }

    public void setXSJH(String XSJH) {
        this.XSJH = XSJH;
    }
}
