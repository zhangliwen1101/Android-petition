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
import cn.aorise.petition.staff.ui.bean.ContactpeopleInfo;
import cn.aorise.petition.staff.ui.bean.PeopleInfo;


/**
 * Created by Administrator on 2017/5/15.
 */

public class WorkWarningDetailContactPeopleAdapter extends BaseAdapter {
    private Context mContext;
    private List<ContactpeopleInfo> data;

    public WorkWarningDetailContactPeopleAdapter(List<ContactpeopleInfo> data, Context mContext) {
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
        final WorkWarningDetailContactPeopleAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (WorkWarningDetailContactPeopleAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_important_matter_detail_people_item,null);
            holder=new WorkWarningDetailContactPeopleAdapter.Holder();
            holder.xm= (TextView) view.findViewById(R.id.txt_xm);
            holder.zjhm= (TextView) view.findViewById(R.id.txt_zjhm);
            holder.dz= (TextView) view.findViewById(R.id.txt_dz);
            view.setTag(holder);
        }

        final ContactpeopleInfo mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.xm.setText(mydata.getXM());
        holder.zjhm.setText(mydata.getZJHM());
        holder.dz.setText(mydata.getDZ());
        return view;

    }
    public class Holder{
        private TextView xm,zjhm,dz;


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
