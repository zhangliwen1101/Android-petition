package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by 李世林 on 2017/10/26.
 */

public class TZZJG {
    private String zzjg;

    @Override
    public String toString() {
        return "TZZJG{" +
                "zzjg='" + zzjg + '\'' +
                '}';
    }

    public String getZzjg() {
        return zzjg;
    }

    public void setZzjg(String zzjg) {
        this.zzjg = zzjg;
    }
}
