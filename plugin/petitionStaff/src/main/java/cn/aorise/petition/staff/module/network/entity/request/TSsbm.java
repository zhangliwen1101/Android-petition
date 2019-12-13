package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/7/10.
 */

public class TSsbm {
    private String SSBM;

    @Override
    public String toString() {
        return "TSsbm{" +
                "SSBM='" + SSBM + '\'' +
                '}';
    }

    public String getSSBM() {
        return SSBM;
    }

    public void setSSBM(String SSBM) {
        this.SSBM = SSBM;
    }
}
