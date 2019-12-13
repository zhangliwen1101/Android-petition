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
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.ui.activity.PetitionStaffstaffDetailInfoActivity;


/**
 * Created by Administrator on 2017/5/15.
 */

public class StaffInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<RStaffInfo> data;

    public StaffInfoAdapter(List<RStaffInfo> data, Context mContext) {
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
        final StaffInfoAdapter.Holder holder;
        if (convertView!=null){
            view=convertView;
            holder= (StaffInfoAdapter.Holder) view.getTag();
        }else{
            view= View.inflate(mContext, R.layout.petition_staff_staff_info_list_item,null);
            holder=new StaffInfoAdapter.Holder();
            holder.title= (TextView) view.findViewById(R.id.txt_title);
            holder.listView= (ListView) view.findViewById(R.id.listview);
            holder.imageView= (ImageView) view.findViewById(R.id.img);
            holder.relativeLayout= (RelativeLayout) view.findViewById(R.id.rl);
            holder.textView7= (TextView) view.findViewById(R.id.textView7);
            view.setTag(holder);
            /**/
        }

        final RStaffInfo mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.title.setText(mydata.getZZJG());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.listView.getVisibility()==View.VISIBLE) {
                    holder.imageView.setImageResource(R.drawable.petition_staff_pack_up);
                    holder.listView.setVisibility(View.GONE);
                    holder.textView7.setVisibility(View.GONE);
                }else {
                    holder.imageView.setImageResource(R.drawable.petition_staff_unwind);
                    holder.listView.setVisibility(View.VISIBLE);
                    holder.textView7.setVisibility(View.VISIBLE);
                }
            }
        });
            StaffInfoListAdapter adapter = new StaffInfoListAdapter(mydata.getWorkpeopleList(), mContext);
            holder.listView.setAdapter(adapter);
            holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(mContext, PetitionStaffstaffDetailInfoActivity.class);
                    intent.putExtra("dhhm",mydata.getWorkpeopleList().get(position).getDHHM());
                    mContext.startActivity(intent);
                }
            });

        return view;

    }
    public class Holder{
        private TextView title,textView7;
        private ListView listView;
        private ImageView imageView;
        private RelativeLayout relativeLayout;

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
