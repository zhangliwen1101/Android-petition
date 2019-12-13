package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/12.
 */

public class TAnalyze {
    private String type;
    private String valueOne;
    private String valueTwo;
    private String condition;


    @Override
    public String toString() {
        return "TAnalyze{" +
                "type='" + type + '\'' +
                ", valueOne='" + valueOne + '\'' +
                ", valueTwo='" + valueTwo + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueOne() {
        return valueOne;
    }

    public void setValueOne(String valueOne) {
        this.valueOne = valueOne;
    }

    public String getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(String valueTwo) {
        this.valueTwo = valueTwo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
