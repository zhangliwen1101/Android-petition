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
import cn.aorise.petition.staff.common.MpChartAndroidUtil;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzeBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAnalyzexinfangbumenjishishoulilvBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze01;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyze;
import cn.aorise.petition.staff.ui.adapter.SpinnerAdapter;
import cn.aorise.petition.staff.ui.adapter.SpinnerStringAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.fragment.ResponseInfo;
import cn.aorise.petition.staff.ui.fragment.WentiLeixingInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/9.
 */

public class PetitionStaffAnalyzeOnTimeActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffActivityAnalyzexinfangbumenjishishoulilvBinding mBinding;

    private BarChart barChart;// 柱形图控件
    private BarData data;// x轴数据
    private BarDataSet dataSet;// Y轴数据
    // TODO: 2017/10/26 柱状图
    private String[] st={"总量","按期办结","未按期办结"};
    // TODO: 2017/10/26 饼状图
    private String[] x = new String[] {"按期办结","未按期办结"};
    private Float[] y;
    private List <String> code = new ArrayList<>();
    private SpinnerStringAdapter arr_adapter01,arr_adapter02;
    private String type,xingshi;
    private List<String> spinnerData = new ArrayList<>();
    private List<String> spinnerData02 = new ArrayList<>();

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
                    GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString(), type,xingshi);
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
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_analyzexinfangbumenjishishoulilv);
        mBinding.endTime.setText(gettime());
        mBinding.startTime.setText(getMonthAgo(new Date()));
        arr_adapter02 = new SpinnerStringAdapter(spinnerData02,this);
        mBinding.llZerendanwei.setVisibility(View.GONE);
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
        arr_adapter01 = new SpinnerStringAdapter(spinnerData, this);
        mBinding.spLeixing.setAdapter(arr_adapter01);
        getOrange();

        mBinding.startTime.setOnClickListener(this);
        mBinding.endTime.setOnClickListener(this);
        mBinding.btnQuery.setOnClickListener(this);
        mBinding.barchart.setDragEnabled(true);
        mBinding.barchart.setScaleEnabled(true);
        // barChart.invalidate();
        mBinding.txtTitle.setText("信访部门及时受理率");


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
                                showToast("网络错误");
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

                                GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString(), type,xingshi);
                            }

                            @Override
                            public void onMock(APIResult<List<WentiLeixingInfo>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
   /* @SuppressWarnings("deprecation")
    private void init(BarChart barChart,List<Integer> data1) {
        barChart.setDescription("");
        XAxis xAxis = barChart.getXAxis();// x轴
        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        YAxis yyAxis = barChart.getAxisLeft();
        yyAxis.setAxisLineWidth(0);
        yyAxis.setStartAtZero(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴坐标在底部，默认在顶部
        ArrayList<String> xvals = new ArrayList<String>();
        for (int i=0;i<st.length;i++){
            xvals.add(st[i]);
        }

        data = new BarData(xvals, getDataSet(data1));
    }*/

    /*private ArrayList<IBarDataSet> getDataSet(List<Integer> data) {

        ArrayList<IBarDataSet> datasets = null;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < data.size(); i++) {
            yVals1.add(new BarEntry(data.get(i),i));
        }

        BarDataSet barDataSet1 = new BarDataSet(yVals1, "数量");
        barDataSet1.setColor(Color.parseColor("#FCB94E"));
        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry,
                                            int dataSetIndex, ViewPortHandler viewPortHandler) {
                // TODO Auto-generated method stub
                BigDecimal b = new BigDecimal(value);
                float f1 = (float) b.setScale(1, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();

                return f1 + "";
            }
        });

        datasets = new ArrayList<IBarDataSet>();
        datasets.add(barDataSet1);


        // barChart.invalidate();
        return datasets;
    }*/

    private void GetAnalyze(String dataStart, String dataEnd, String type,String xingshi) {

        ResponseInfo t = new ResponseInfo();
        t.setValueOne(dataStart);
        t.setValueTwo(dataEnd);
        t.setProblem(type);
        if (xingshi.equals("全部")) {
            t.setFormof("");
        } else {
            t.setFormof(xingshi);
        }
        System.out.println(GsonUtil.toJson(t));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetAnqiBanjieLv(GsonUtil.toJson(t))
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

                                //init(mBinding.barchart, rAnalyzeAPIResult.getData());

                                System.out.println(rAnalyzeAPIResult.toString());
                                try {
                                    ArrayList<String> xValues = new ArrayList<String>();
                                    String[] years = {"%",""};
                                    List<Float> counts = rAnalyzeAPIResult.getData();
                                    for (int i = 0; i < years.length; i++) {
                                        xValues.add(years[i]);
                                    }
                                    ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
                                    for (int i = 0; i < counts.size(); i++) {
                                        if (i>=5) {
                                            /*float value = (float) (Math.random() * range*//*100以内的随机数*//*) + 3;*/
                                            if (counts.get(i) == null) {
                                                if (i==5) {
                                                    yValues.add(new BarEntry(0, 0));
                                                } else
                                                    yValues.add(new BarEntry(0, 1));
                                            } else {
                                                if (i == 5) {
                                                    yValues.add(new BarEntry(counts.get(i) , 0));
                                                } else
                                                    yValues.add(new BarEntry(counts.get(i) , 1));
                                            }
                                        }
                                    }
                                    // y轴的数据集合
                                    BarDataSet barDataSet = new BarDataSet(yValues, "%");
                                    ArrayList<Integer> colors = new ArrayList<Integer>();
                                    colors.add(getResources().getColor(R.color.green));
                                    colors.add(getResources().getColor(R.color.yellow));
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
                                    MpChartAndroidUtil.showBarChart1(PetitionStaffAnalyzeOnTimeActivity.this, mBinding.barchart, barData);
                                } catch (Exception e) {

                                }
                                if (rAnalyzeAPIResult.getData().get(0)!=null) {
                                    mBinding.txt1.setText("总数："+rAnalyzeAPIResult.getData().get(0));
                                } else {
                                    mBinding.txt1.setText("总数："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(1)!=null) {
                                    mBinding.txt2.setText("已受理-小计："+rAnalyzeAPIResult.getData().get(1));
                                } else {
                                    mBinding.txt2.setText("已受理-小计："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(2)!=null) {
                                    mBinding.txt3.setText("已受理-及时受理件数："+rAnalyzeAPIResult.getData().get(2));
                                } else {
                                    mBinding.txt3.setText("已受理-及时受理件数："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(3)!=null) {
                                    mBinding.txt4.setText("已受理-超期受理件数："+rAnalyzeAPIResult.getData().get(3));
                                } else {
                                    mBinding.txt4.setText("已受理-超期受理件数："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(4)!=null) {
                                    mBinding.txt5.setText("超期未受理件数："+rAnalyzeAPIResult.getData().get(4));
                                } else {
                                    mBinding.txt5.setText("超期未受理件数："+"0");
                                }
                                if (rAnalyzeAPIResult.getData().get(5)!=null) {
                                    mBinding.txt6.setText("及时受理率："+rAnalyzeAPIResult.getData().get(5)+"%");
                                } else {
                                    mBinding.txt6.setText("及时受理率："+"0.0%");
                                }
                                if (rAnalyzeAPIResult.getData().get(6)!=null) {
                                    mBinding.txt7.setText("受理率："+rAnalyzeAPIResult.getData().get(6)+"%");
                                } else {
                                    mBinding.txt7.setText("受理率："+"0.0%");
                                }
                            }

                            @Override
                            public void onMock(APIResult<List<Float>> rAnalyzeAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }





}