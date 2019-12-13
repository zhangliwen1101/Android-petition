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
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;


/**
 * Created by Administrator on 2017/5/15.
 */

public class ImportantPetitionMatterAdapter extends BaseAdapter {
    private Context mContext;
    private List<RImportantPetitionMatter> data;

    public ImportantPetitionMatterAdapter(List<RImportantPetitionMatter> data, Context mContext) {
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
        final ImportantPetitionMatterAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (ImportantPetitionMatterAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_important_petition_matter_item,null);
            holder=new ImportantPetitionMatterAdapter.Holder();
            holder.bh= (TextView) view.findViewById(R.id.txt_bh);
            holder.xm= (TextView) view.findViewById(R.id.txt_name);
            holder.lx= (TextView) view.findViewById(R.id.txt_lx);
            holder.jb= (TextView) view.findViewById(R.id.txt_jb);
            holder.sj= (TextView) view.findViewById(R.id.txt_sj);
            view.setTag(holder);
        }

        final RImportantPetitionMatter mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.bh.setText(mydata.getBH());
        holder.xm.setText(mydata.getXM());
        holder.lx.setText(mydata.getLX());
        holder.jb.setText(mydata.getJB());
        holder.sj.setText(mydata.getSJ());

        return view;

    }
    public class Holder{
        private TextView bh,xm,lx,jb,sj;


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
