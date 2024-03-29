package cn.aorise.petition.staff.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningList;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WorkWarningListAdapter extends BaseAdapter {
    private Context mContext;
    private List<RWorkWarningList> data;

    public WorkWarningListAdapter(List<RWorkWarningList> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
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
        final Holder holder;
        if (convertView != null) {
            view = convertView;
            holder = (Holder) view.getTag();
        } else {
            view = View.inflate(mContext, R.layout.petition_staff_activity_work_warning_list_item, null);
            holder = new Holder();
            holder.num = (TextView) view.findViewById(R.id.textView19);
            holder.content = (TextView) view.findViewById(R.id.textView26);
            holder.date = (TextView) view.findViewById(R.id.textView23);
            holder.answer_content = (TextView) view.findViewById(R.id.textView28);
            holder.icon = (ImageView) view.findViewById(R.id.imageView15);
            holder.tx1= (TextView) view.findViewById(R.id.textView27);

            view.setTag(holder);
        }
        final RWorkWarningList mydata = data.get(position);
        holder.num.setText(mydata.getBH());
        holder.content.setText(mydata.getNR());
        holder.date.setText(mydata.getLFRQ());
        holder.answer_content.setText(mydata.getXBSJ());


        return view;
    }

    public class Holder {
        private TextView num, content, date, answer_content,tx1;
        private ImageView icon;

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
