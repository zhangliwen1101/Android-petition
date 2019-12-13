package cn.aorise.petition.staff.ui.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.FileUtils;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.ui.activity.PetitionStaffImportantMonitorAddMatterAdjunctActivity;
import cn.aorise.petition.staff.ui.bean.StaffInfo;


/**
 * Created by Administrator on 2017/5/15.
 */

public class StaffInfoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<StaffInfo> data;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public StaffInfoListAdapter(List<StaffInfo> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();//返回listiview数目加1
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
        final StaffInfoListAdapter.Holder holder;
        if (convertView != null) {
            view = convertView;
            holder = (StaffInfoListAdapter.Holder) view.getTag();
        } else {
            view = View.inflate(mContext, R.layout.petition_staff_staff_info_list_list_item, null);
            holder = new StaffInfoListAdapter.Holder();
            holder.txt_xm = (TextView) view.findViewById(R.id.txt_xm);
            holder.txt_dhhm = (TextView) view.findViewById(R.id.txt_dhhm);
            holder.txt_zw = (TextView) view.findViewById(R.id.txt_zw);
            holder.img_phone = (ImageView) view.findViewById(R.id.img_phone);
            holder.img_message = (ImageView) view.findViewById(R.id.img_message);
            view.setTag(holder);
        }

        final StaffInfo mydata = data.get(position);
        //原先的正常数据的显示，操作等
        holder.txt_xm.setText(mydata.getXM());
        holder.txt_dhhm.setText(mydata.getDHHM());
        holder.txt_zw.setText(mydata.getZW());
        holder.img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext).setTitle("拨打电话？")//设置对话框标题
                        /*.setMessage("")//设置显示的内容*/
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                if (mydata.getDHHM().length() != 0) {
                                    if (ContextCompat.checkSelfPermission(mContext,
                                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // 没有获得授权，申请授权
                                        if (ActivityCompat.shouldShowRequestPermissionRationale((BaseActivity) mContext,
                                                Manifest.permission.CALL_PHONE)) {
                                            // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                            // 弹窗需要解释为何需要该权限，再次请求授权


                                            // 帮跳转到该应用的设置界面，让用户手动授权
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                                            intent.setData(uri);
                                            mContext.startActivity(intent);
                                        } else {
                                            // 不需要解释为何需要该权限，直接请求授权
                                            ActivityCompat.requestPermissions((BaseActivity) mContext,
                                                    new String[]{Manifest.permission.CALL_PHONE},
                                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                        }
                                    } else {
                                        // 已经获得授权，可以打电话
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mydata.getDHHM()));
                                        mContext.startActivity(intent);
                                    }
                                }

                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub

                    }

                }).show();//在按键响应事件中显示此对话框


            }
        });

        holder.img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsToUri = Uri.parse("smsto:");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                sendIntent.putExtra("address", mydata.getDHHM()); //电话号码，这行去掉的话，默认就没有电话
                sendIntent.putExtra("sms_body","");
                sendIntent.setType("vnd.android-dir/mms-sms");
                mContext.startActivity(sendIntent);
            }
        });


        return view;

    }

    public class Holder {
        private TextView txt_xm, txt_dhhm, txt_zw;
        private ImageView img_phone, img_message;

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
