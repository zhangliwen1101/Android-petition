package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/13.
 */

public class TAnalyzeType {
    private String type;
    private String value;

    @Override
    public String toString() {
        return "TAnalyzeType{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
