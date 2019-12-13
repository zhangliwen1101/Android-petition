package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TLogin {
    private String YHM;
    private String MM;

    @Override
    public String toString() {
        return "TLogin{" +
                "YHM='" + YHM + '\'' +
                ", MM='" + MM + '\'' +
                '}';
    }

    public String getYHM() {
        return YHM;
    }

    public void setYHM(String YHM) {
        this.YHM = YHM;
    }

    public String getMM() {
        return MM;
    }

    public void setMM(String MM) {
        this.MM = MM;
    }
}
