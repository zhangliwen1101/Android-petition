package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/15.
 */

public class Value {
    private float DSL;
    private float YSL;
    private float DBL;
    private float YBL;
    private float DDB;
    private float YDB;
    private String LX;

    @Override
    public String toString() {
        return "Value{" +
                "DSL='" + DSL + '\'' +
                ", YSL='" + YSL + '\'' +
                ", DBL='" + DBL + '\'' +
                ", YBL='" + YBL + '\'' +
                ", DDB='" + DDB + '\'' +
                ", YDB='" + YDB + '\'' +
                ", LX='" + LX + '\'' +
                '}';
    }

    public float getDSL() {
        return DSL;
    }

    public void setDSL(float DSL) {
        this.DSL = DSL;
    }

    public float getYSL() {
        return YSL;
    }

    public void setYSL(float YSL) {
        this.YSL = YSL;
    }

    public float getDBL() {
        return DBL;
    }

    public void setDBL(float DBL) {
        this.DBL = DBL;
    }

    public float getYBL() {
        return YBL;
    }

    public void setYBL(float YBL) {
        this.YBL = YBL;
    }

    public float getDDB() {
        return DDB;
    }

    public void setDDB(float DDB) {
        this.DDB = DDB;
    }

    public float getYDB() {
        return YDB;
    }

    public void setYDB(float YDB) {
        this.YDB = YDB;
    }

    public String getLX() {
        return LX;
    }

    public void setLX(String LX) {
        this.LX = LX;
    }
}
