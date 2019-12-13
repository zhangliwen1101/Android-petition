package cn.aorise.petition.staff.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.ui.activity.PetitionStaffstaffDetailInfoActivity;


/**
 * Created by Administrator on 2017/5/15.
 */

public class ImportantPetitionPeopleAdapter extends BaseAdapter {
    private Context mContext;
    private List<RImportantPetitionPeople> data;

    public ImportantPetitionPeopleAdapter(List<RImportantPetitionPeople> data, Context mContext) {
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
        final ImportantPetitionPeopleAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (ImportantPetitionPeopleAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_activity_important_object_list_item,null);
            holder=new ImportantPetitionPeopleAdapter.Holder();
            holder.txt_name= (TextView) view.findViewById(R.id.txt_name);
            holder.txt_dhhm= (TextView) view.findViewById(R.id.txt_number);
            holder.txt_zjhm= (TextView) view.findViewById(R.id.txt_card_number);
            holder.txt_area= (TextView) view.findViewById(R.id.txt_contact_area);
            holder.txt_address= (TextView) view.findViewById(R.id.txt_contact_address);
            view.setTag(holder);
        }

        final RImportantPetitionPeople mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.txt_name.setText(mydata.getXM());
        holder.txt_dhhm.setText(mydata.getDHHM());
        holder.txt_zjhm.setText(mydata.getZJHM());
        holder.txt_area.setText(mydata.getDQ());
        holder.txt_address.setText(mydata.getXXDZ());

        return view;

    }
    public class Holder{
        private TextView txt_name,txt_dhhm,txt_zjhm,txt_area,txt_address;


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
