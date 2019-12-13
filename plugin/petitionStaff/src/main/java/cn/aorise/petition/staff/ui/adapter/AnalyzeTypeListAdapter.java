package cn.aorise.petition.staff.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.module.network.entity.response.RDmMc;
import cn.aorise.petition.staff.module.network.entity.response.Value;

/**
 * Created by Administrator on 2017/5/5.
 */

public class AnalyzeTypeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Value> data;

    public AnalyzeTypeListAdapter(List<Value> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return  data.size();//返回listiview数目加1
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final AnalyzeTypeListAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (AnalyzeTypeListAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_analyze_type_list_item,null);
            holder=new AnalyzeTypeListAdapter.Holder();
            holder.txt1= (TextView) view.findViewById(R.id.txt1);
            holder.txt2= (TextView) view.findViewById(R.id.txt2);
            holder.txt3= (TextView) view.findViewById(R.id.txt3);
            holder.txt4= (TextView) view.findViewById(R.id.txt4);
            holder.txt5= (TextView) view.findViewById(R.id.txt5);
            holder.txt6= (TextView) view.findViewById(R.id.txt6);
            holder.txt7= (TextView) view.findViewById(R.id.txt7);

            view.setTag(holder);
        }

        final Value mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.txt1.setText(mydata.getLX());
        holder.txt2.setText((int)mydata.getDSL()+"");
        holder.txt3.setText((int)mydata.getYSL()+"");
        holder.txt4.setText((int)mydata.getDBL()+"");
        holder.txt5.setText((int)mydata.getYBL()+"");
        holder.txt6.setText((int)mydata.getDDB()+"");
        holder.txt7.setText((int)mydata.getYDB()+"");

        return view;

    }
    public class Holder{
        private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7;

    }
    public static String GetStringFromLong(long millis) {//yyyy年MM月dd日HH时mm分
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(millis);
        int i = Integer.parseInt(String.valueOf(millis));
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
}
