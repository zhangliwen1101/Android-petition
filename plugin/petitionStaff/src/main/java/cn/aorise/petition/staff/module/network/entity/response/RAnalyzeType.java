package cn.aorise.petition.staff.module.network.entity.response;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RAnalyzeType {
    private  Total totall;
    private List<Value> value;

    @Override
    public String toString() {
        return "RAnalyzeType{" +
                "totall=" + totall +
                ", value=" + value +
                '}';
    }

    public Total getTotall() {
        return totall;
    }

    public void setTotall(Total totall) {
        this.totall = totall;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }
}
