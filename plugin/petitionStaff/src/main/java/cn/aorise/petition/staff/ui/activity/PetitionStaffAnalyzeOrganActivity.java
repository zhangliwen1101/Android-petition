package cn.aorise.petition.staff.ui.activity;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyze2Binding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzeBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzeOrganBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzeTypeBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze01;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyzeType;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyze;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyzeOrgan;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyzeType;
import cn.aorise.petition.staff.module.network.entity.response.Value;
import cn.aorise.petition.staff.ui.adapter.AnalyzeOrganListAdapter;
import cn.aorise.petition.staff.ui.adapter.AnalyzeTypeListAdapter;
import cn.aorise.petition.staff.ui.adapter.SpinnerAdapter;
import cn.aorise.petition.staff.ui.adapter.SpinnerStringAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/9.
 */

public class PetitionStaffAnalyzeOrganActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffActivityAnalyze2Binding mBinding;
    private List<RAnalyzeOrgan> listdata=new ArrayList<>();
    private AnalyzeOrganListAdapter adapter;

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.btn_query==id) {
            if (mBinding.startTime.getText().toString().equals("")||mBinding.endTime.getText().toString().equals("")){
                showToast("请填写时间信息");
            } else {
                if (getTime(mBinding.endTime.getText().toString()) < getTime(mBinding.startTime.getText().toString())) {
                    showToast("结束时间不能小于开始时间");
                } else {
                    GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString());
                }
            }
        } else if (R.id.start_time==id) {
            settime(mBinding.startTime);
        } else if (R.id.end_time==id) {
            settime(mBinding.endTime);
        }
    }

    @Override
    protected void initData() {

    }

    private void settime (final TextView txt) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                txt.setText(DateFormat.format("yyy-MM-dd", c));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    public static Long getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return Long.parseLong(re_time);
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_analyze2);
        mBinding.startTime.setOnClickListener(this);
        mBinding.endTime.setOnClickListener(this);
        mBinding.llResponsible.setVisibility(View.GONE);
        mBinding.btnQuery.setOnClickListener(this);
        adapter=new AnalyzeOrganListAdapter(listdata,this);
        mBinding.listview.setAdapter(adapter);
        mBinding.txtTitle.setText("处理机关统计");
        GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString());

    }

    @Override
    protected void initEvent() {

    }



    private void GetAnalyze(String dataStart, String dataEnd) {
        TAnalyze01 t= new TAnalyze01();
        t.setDateStart(dataStart);
        t.setDateEnd(dataEnd);
        Subscription subscription = PetitionStaffApiService.Factory.create().GetAnalyzeOrgan(GsonUtil.toJson(t))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RAnalyze>>() {
                        }.getType(), new APICallback<APIResult<List<RAnalyzeOrgan>>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<RAnalyzeOrgan>> listAPIResult) {
                                listdata.clear();
                                for (int i = 0; i < listAPIResult.getData().size(); i++) {
                                    RAnalyzeOrgan rAnalyzeOrgan=new RAnalyzeOrgan();
                                    rAnalyzeOrgan.setJigou(listAPIResult.getData().get(i).getJigou());
                                    rAnalyzeOrgan.setLaifang(listAPIResult.getData().get(i).getLaifang());
                                    rAnalyzeOrgan.setLaixin(listAPIResult.getData().get(i).getLaixin());
                                    rAnalyzeOrgan.setLaidian(listAPIResult.getData().get(i).getLaidian());
                                    rAnalyzeOrgan.setWangxin(listAPIResult.getData().get(i).getWangxin());
                                    listdata.add(rAnalyzeOrgan);
                                }
                                adapter.notifyDataSetChanged();



                            }

                            @Override
                            public void onMock(APIResult<List<RAnalyzeOrgan>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }







}
