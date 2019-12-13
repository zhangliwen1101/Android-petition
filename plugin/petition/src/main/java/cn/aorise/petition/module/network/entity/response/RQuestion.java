package cn.aorise.petition.module.network.entity.response;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RQuestion {
    private String MC;
    private String DM;
    private String FJDM;

    @Override
    public String toString() {
        return "RQuestion{" +
                "MC='" + MC + '\'' +
                ", DM='" + DM + '\'' +
                ", FJDM='" + FJDM + '\'' +
                '}';
    }

    public String getFJDM() {
        return FJDM;
    }

    public void setFJDM(String FJDM) {
        this.FJDM = FJDM;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public Object getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }
}
