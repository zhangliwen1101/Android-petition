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
import cn.aorise.petition.staff.common.MpChartAndroidUtil;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyze01Binding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzeBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzecanpinglvBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze01;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyze;
import cn.aorise.petition.staff.module.network.entity.response.RZerenDanwei;
import cn.aorise.petition.staff.ui.adapter.SpinnerStringAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.fragment.ResponseInfo;
import cn.aorise.petition.staff.ui.fragment.WentiLeixingInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 李世林 on 2017/10/26.
 */

public class PetitionStaffAnalyzeBumenmanyilvActivity01 extends PetitionStaffBaseActivity implements View.OnClickListener {
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffActivityAnalyzecanpinglvBinding mBinding;

    private BarChart barChart;// 柱形图控件
    private BarData data;// x轴数据
    private BarDataSet dataSet;// Y轴数据
    // TODO: 2017/10/26 柱状图
    private String[] st = {"总量", "满意", "基本满意", "不满意"};
    // TODO: 2017/10/26 饼状图
    private String[] x = new String[]{"满意", "基本满意", "不满意"};
    private Float[] y;
    private List<String> spinnerData = new ArrayList<>();
    private List<String> spinnerData02 = new ArrayList<>();
    private List<String> spinnerDataZeRenDanWei = new ArrayList<>();
    private List <String> code = new ArrayList<>();
    private List<String> codeZeRenDanWei = new ArrayList<>();
    private SpinnerStringAdapter arr_adapter01,arr_adapter02,arr_adapter03;
    private String type,xingshi,ZeRenDanWei;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.btn_query == id) {
            if (mBinding.startTime.getText().toString().equals("") || mBinding.endTime.getText().toString().equals("")) {
                showToast("请填写时间信息");
            } else {
                if (getTime(mBinding.endTime.getText().toString()) < getTime(mBinding.startTime.getText().toString())) {
                    showToast("结束时间不能小于开始时间");
                } else {
                    GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString(), type,xingshi,ZeRenDanWei);
                }
            }
        } else if (R.id.start_time == id) {
            settime(mBinding.startTime);
        } else if (R.id.end_time == id) {
            settime(mBinding.endTime);
        }
    }

    @Override
    protected void initData() {

    }

    private void settime(final TextView txt) {
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
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return Long.parseLong(re_time);
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding = DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_analyzecanpinglv);
        arr_adapter02 = new SpinnerStringAdapter(spinnerData02,this);
        mBinding.endTime.setText(gettime());
        mBinding.startTime.setText(getMonthAgo(new Date()));
        mBinding.spXingshi.setAdapter(arr_adapter02);
        spinnerData02.add("全部");
        spinnerData02.add("来访");
        spinnerData02.add("网信");
        spinnerData02.add("来信");
        spinnerData02.add("来电");
        xingshi = spinnerData02.get(0);
        arr_adapter02.notifyDataSetChanged();
        mBinding.spXingshi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xingshi = spinnerData02.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //适配器
        arr_adapter01 = new SpinnerStringAdapter(spinnerData, this);
        mBinding.spLeixing.setAdapter(arr_adapter01);
        arr_adapter03 = new SpinnerStringAdapter(spinnerDataZeRenDanWei,this);
        mBinding.spZerendanwei.setAdapter(arr_adapter03);
        getOrange();
        getZeRenDanWei();

        mBinding.startTime.setOnClickListener(this);
        mBinding.endTime.setOnClickListener(this);

        mBinding.btnQuery.setOnClickListener(this);
        mBinding.barchart.setDragEnabled(true);
        mBinding.barchart.setScaleEnabled(true);
        // barChart.invalidate();
        mBinding.txtTitle.setText("参评率");


    }

    @Override
    protected void initEvent() {

    }
    private void getOrange() {
        Subscription subscription = PetitionStaffApiService.Factory.create().Getwentileixing("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RAnalyze>>() {
                        }.getType(), new APICallback<APIResult<List<WentiLeixingInfo>>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable throwable) {
                                //showToast("网络错误");
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<WentiLeixingInfo>> listAPIResult) {
                                System.out.println(listAPIResult);
                                spinnerData.add("全部");
                                code.add("");
                                for (int i = 0; i < listAPIResult.getData().size(); i++) {
                                    spinnerData.add(listAPIResult.getData().get(i).getMC());
                                    code.add(listAPIResult.getData().get(i).getDM());
                                }
                                arr_adapter01.notifyDataSetChanged();
                                type = code.get(0);
                                mBinding.spLeixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        type = code.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString(), type,xingshi,ZeRenDanWei);
                            }

                            @Override
                            public void onMock(APIResult<List<WentiLeixingInfo>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }private void getZeRenDanWei() {

        Subscription subscription = PetitionStaffApiService.Factory.create().GetResponsible("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RAnalyze>>() {
                        }.getType(), new APICallback<APIResult<List<RZerenDanwei>>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable throwable) {
                                //showToast("网络错误");
                                //System.out.println(throwable.getMessage()+throwable.getCause());
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<RZerenDanwei>> listAPIResult) {
                                System.out.println(listAPIResult);
                                spinnerDataZeRenDanWei.add("全部");
                                codeZeRenDanWei.add("");
                                for (int i = 0; i < listAPIResult.getData().size(); i++) {
                                    spinnerDataZeRenDanWei.add(listAPIResult.getData().get(i).getJGMC());
                                    codeZeRenDanWei.add(listAPIResult.getData().get(i).getJGDM());
                                }
                                arr_adapter03.notifyDataSetChanged();
                                ZeRenDanWei = spinnerDataZeRenDanWei.get(0);
                                mBinding.spZerendanwei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ZeRenDanWei = spinnerDataZeRenDanWei.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString(), type,xingshi,ZeRenDanWei);
                            }

                            @Override
                            public void onMock(APIResult<List<RZerenDanwei>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void GetAnalyze(String dataStart, String dataEnd, String type,String xingshi,String ZeRenDanWei) {

        ResponseInfo t = new ResponseInfo();
        t.setValueOne(dataStart);
        t.setValueTwo(dataEnd);
        t.setProblem(type);
        if (xingshi.equals("全部")) {
            t.setFormof("");
        } else {
            t.setFormof(xingshi);
        }
        if (ZeRenDanWei.equals("全部")) {
            t.setZrdw("");
        } else {
            t.setZrdw(ZeRenDanWei);
        }
        System.out.println(GsonUtil.toJson(t));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetCanPingLv(GsonUtil.toJson(t))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RAnalyze>>() {
                        }.getType(), new APICallback<APIResult<List<Float>>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<Float>> rAnalyzeAPIResult) {

                                System.out.println(rAnalyzeAPIResult.toString());
                                try {
                                    ArrayList<String> xValues = new ArrayList<String>();
                                    String[] years = {"%"};
                                    List<Float> counts = rAnalyzeAPIResult.getData();
                                    for (int i = 0; i < years.length; i++) {
                                        xValues.add(years[i]);
                                    }
                                    ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
                                    for (int i = 0; i < counts.size(); i++) {
                                        if (i>=0) {
                                            /*float value = (float) (Math.random() * range*//*100以内的随机数*//*) + 3;*/
                                            if (counts.get(i) == null) {
                                                if (i==0) {
                                                    yValues.add(new BarEntry(0, 0));
                                                }
                                            } else {
                                                if (i == 0) {
                                                    yValues.add(new BarEntry(counts.get(i) , 0));
                                                }
                                            }
                                        }
                                    }
                                    // y轴的数据集合
                                    BarDataSet barDataSet = new BarDataSet(yValues, "%");
                                    ArrayList<Integer> colors = new ArrayList<Integer>();
                                    colors.add(getResources().getColor(R.color.green));
                                    barDataSet.setColors(colors);
                                    barDataSet.setBarSpacePercent(50f);
                                    barDataSet.setValueFormatter(new ValueFormatter() {
                                        @Override
                                        public String getFormattedValue(float value, Entry entry,
                                                                        int dataSetIndex, ViewPortHandler viewPortHandler) {
                                            // TODO Auto-generated method stub
                                            BigDecimal b = new BigDecimal(value);
                                            Float f1 = (Float) b.setScale(1, BigDecimal.ROUND_HALF_UP)
                                                    .floatValue();
                                            return f1 + "";
                                        }
                                    });
                                    ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
                                    barDataSets.add(barDataSet); // add the datasets

                                    BarData barData = new BarData(xValues, barDataSets);
                                    MpChartAndroidUtil.showBarChart1(PetitionStaffAnalyzeBumenmanyilvActivity01.this, mBinding.barchart, barData);
                                } catch (Exception e) {

                                }
                                if (rAnalyzeAPIResult.getData().get(2)!=null) {
                                    mBinding.txt1.setText("已评价："+rAnalyzeAPIResult.getData().get(2));
                                } else {
                                    mBinding.txt1.setText("已评价："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(1)!=null) {
                                    mBinding.txt2.setText("未评价："+rAnalyzeAPIResult.getData().get(1));
                                } else {
                                    mBinding.txt2.setText("未评价："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(0)!=null) {
                                    mBinding.txt3.setText("参评率："+rAnalyzeAPIResult.getData().get(0)+"%");
                                } else {
                                    mBinding.txt3.setText("参评率："+"0");
                                }
                            }

                            @Override
                            public void onMock(APIResult<List<Float>> rAnalyzeAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }







}
