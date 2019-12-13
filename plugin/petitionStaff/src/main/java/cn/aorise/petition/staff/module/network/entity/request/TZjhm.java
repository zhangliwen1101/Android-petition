package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/31.
 */

public class TZjhm {
    private String ZJHM;

    @Override
    public String toString() {
        return "TZjhm{" +
                "ZJHM='" + ZJHM + '\'' +
                '}';
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }
}
