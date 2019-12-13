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
import cn.aorise.petition.staff.module.network.entity.response.RDMIDMC;
import cn.aorise.petition.staff.module.network.entity.response.RDmMc;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ZJLXAdapter extends BaseAdapter {
    private Context mContext;
    private List<RDMIDMC> data;

    public ZJLXAdapter(List<RDMIDMC> data, Context mContext) {
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
        final ZJLXAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (ZJLXAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_request_add_address_list_item,null);
            holder=new ZJLXAdapter.Holder();
            holder.st= (TextView) view.findViewById(R.id.textView3);

            view.setTag(holder);
        }

        final RDMIDMC mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.st.setText(mydata.getMC());
        return view;

    }
    public class Holder{
        private TextView st;

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