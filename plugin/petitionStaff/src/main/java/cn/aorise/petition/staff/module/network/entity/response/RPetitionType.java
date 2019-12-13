package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/1.
 */

public class RPetitionType {
    private String ID;
    private String MC;
    private String DM;

    @Override
    public String toString() {
        return "RPetitionType{" +
                "ID='" + ID + '\'' +
                ", MC='" + MC + '\'' +
                ", DM='" + DM + '\'' +
                '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }
}
