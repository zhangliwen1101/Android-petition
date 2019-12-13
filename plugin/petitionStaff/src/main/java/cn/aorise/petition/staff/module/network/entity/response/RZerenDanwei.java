package cn.aorise.petition.staff.module.network.entity.response;

import java.util.List;

/**
 * Created by 李世林 on 2017/10/26.
 */

public class RZerenDanwei {

    /**
     * ID : 0613387a1a9b4e42846cf7e6dc430b2f
     * JGMC : 安全生产监督管理局
     * JGDM : 0019
     * SJJG : 组织机构
     * SJDM : 00001
     * PX : null
     * SFSC : 0
     * CJR : null
     * CJRID : null
     * CJSJ : null
     * FZR : null
     * FZRHM : null
     * BGDZ : null
     * DbPrimaryKeys : ["ID"]
     * DbTableName : t_jigou
     * DefaultSelectFields : *
     * DefaultSortBy : ID desc
     */

    private String ID;
    private String JGMC;
    private String JGDM;
    private String SJJG;
    private String SJDM;
    private Object PX;
    private int SFSC;
    private Object CJR;
    private Object CJRID;
    private Object CJSJ;
    private Object FZR;
    private Object FZRHM;
    private Object BGDZ;
    private String DbTableName;
    private String DefaultSelectFields;
    private String DefaultSortBy;
    private List<String> DbPrimaryKeys;

    @Override
    public String toString() {
        return "RZerenDanwei{" +
                "ID='" + ID + '\'' +
                ", JGMC='" + JGMC + '\'' +
                ", JGDM='" + JGDM + '\'' +
                ", SJJG='" + SJJG + '\'' +
                ", SJDM='" + SJDM + '\'' +
                ", PX=" + PX +
                ", SFSC=" + SFSC +
                ", CJR=" + CJR +
                ", CJRID=" + CJRID +
                ", CJSJ=" + CJSJ +
                ", FZR=" + FZR +
                ", FZRHM=" + FZRHM +
                ", BGDZ=" + BGDZ +
                ", DbTableName='" + DbTableName + '\'' +
                ", DefaultSelectFields='" + DefaultSelectFields + '\'' +
                ", DefaultSortBy='" + DefaultSortBy + '\'' +
                ", DbPrimaryKeys=" + DbPrimaryKeys +
                '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJGMC() {
        return JGMC;
    }

    public void setJGMC(String JGMC) {
        this.JGMC = JGMC;
    }

    public String getJGDM() {
        return JGDM;
    }

    public void setJGDM(String JGDM) {
        this.JGDM = JGDM;
    }

    public String getSJJG() {
        return SJJG;
    }

    public void setSJJG(String SJJG) {
        this.SJJG = SJJG;
    }

    public String getSJDM() {
        return SJDM;
    }

    public void setSJDM(String SJDM) {
        this.SJDM = SJDM;
    }

    public Object getPX() {
        return PX;
    }

    public void setPX(Object PX) {
        this.PX = PX;
    }

    public int getSFSC() {
        return SFSC;
    }

    public void setSFSC(int SFSC) {
        this.SFSC = SFSC;
    }

    public Object getCJR() {
        return CJR;
    }

    public void setCJR(Object CJR) {
        this.CJR = CJR;
    }

    public Object getCJRID() {
        return CJRID;
    }

    public void setCJRID(Object CJRID) {
        this.CJRID = CJRID;
    }

    public Object getCJSJ() {
        return CJSJ;
    }

    public void setCJSJ(Object CJSJ) {
        this.CJSJ = CJSJ;
    }

    public Object getFZR() {
        return FZR;
    }

    public void setFZR(Object FZR) {
        this.FZR = FZR;
    }

    public Object getFZRHM() {
        return FZRHM;
    }

    public void setFZRHM(Object FZRHM) {
        this.FZRHM = FZRHM;
    }

    public Object getBGDZ() {
        return BGDZ;
    }

    public void setBGDZ(Object BGDZ) {
        this.BGDZ = BGDZ;
    }

    public String getDbTableName() {
        return DbTableName;
    }

    public void setDbTableName(String DbTableName) {
        this.DbTableName = DbTableName;
    }

    public String getDefaultSelectFields() {
        return DefaultSelectFields;
    }

    public void setDefaultSelectFields(String DefaultSelectFields) {
        this.DefaultSelectFields = DefaultSelectFields;
    }

    public String getDefaultSortBy() {
        return DefaultSortBy;
    }

    public void setDefaultSortBy(String DefaultSortBy) {
        this.DefaultSortBy = DefaultSortBy;
    }

    public List<String> getDbPrimaryKeys() {
        return DbPrimaryKeys;
    }

    public void setDbPrimaryKeys(List<String> DbPrimaryKeys) {
        this.DbPrimaryKeys = DbPrimaryKeys;
    }
}
