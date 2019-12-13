package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/30.
 */

public class TAddEvaluate {
    private String XFID;
    private String BMZT;
    private String BMJG;
    private String BMBZ;
    private String SXZT;
    private String SXJG;
    private String SXBZ;

    @Override
    public String toString() {
        return "TAddEvaluate{" +
                "XFID='" + XFID + '\'' +
                ", BMZT='" + BMZT + '\'' +
                ", BMJG='" + BMJG + '\'' +
                ", BMBZ='" + BMBZ + '\'' +
                ", SXZT='" + SXZT + '\'' +
                ", SXJG='" + SXJG + '\'' +
                ", SXBZ='" + SXBZ + '\'' +
                '}';
    }

    public String getXFID() {
        return XFID;
    }

    public void setXFID(String XFID) {
        this.XFID = XFID;
    }

    public String getBMZT() {
        return BMZT;
    }

    public void setBMZT(String BMZT) {
        this.BMZT = BMZT;
    }

    public String getBMJG() {
        return BMJG;
    }

    public void setBMJG(String BMJG) {
        this.BMJG = BMJG;
    }

    public String getBMBZ() {
        return BMBZ;
    }

    public void setBMBZ(String BMBZ) {
        this.BMBZ = BMBZ;
    }

    public String getSXZT() {
        return SXZT;
    }

    public void setSXZT(String SXZT) {
        this.SXZT = SXZT;
    }

    public String getSXJG() {
        return SXJG;
    }

    public void setSXJG(String SXJG) {
        this.SXJG = SXJG;
    }

    public String getSXBZ() {
        return SXBZ;
    }

    public void setSXBZ(String SXBZ) {
        this.SXBZ = SXBZ;
    }
}
