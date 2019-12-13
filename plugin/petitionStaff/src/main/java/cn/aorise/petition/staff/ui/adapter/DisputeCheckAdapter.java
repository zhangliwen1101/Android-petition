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
import cn.aorise.petition.staff.module.network.entity.response.RDisputeCheck;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;


/**
 * Created by Administrator on 2017/5/15.
 */

public class DisputeCheckAdapter extends BaseAdapter {
    private Context mContext;
    private List<RDisputeCheck> data;

    public DisputeCheckAdapter(List<RDisputeCheck> data, Context mContext) {
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
        final DisputeCheckAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (DisputeCheckAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_dispute_check_item,null);
            holder=new DisputeCheckAdapter.Holder();

            holder.lx= (TextView) view.findViewById(R.id.txt_lx);
            holder.jb= (TextView) view.findViewById(R.id.txt_jb);
            holder.nr= (TextView) view.findViewById(R.id.txt_nr);
            holder.dz= (TextView) view.findViewById(R.id.txt_dz);
            view.setTag(holder);
        }

        final RDisputeCheck mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.lx.setText(mydata.getJFLX());
        holder.jb.setText(mydata.getJFJB());
        holder.nr.setText(mydata.getJFNR());
        holder.dz.setText(mydata.getJFDZ());

        return view;

    }
    public class Holder{
        private TextView lx,jb,nr,dz;


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
