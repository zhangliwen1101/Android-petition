package cn.aorise.petition.staff.ui.activity;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
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
import cn.aorise.petition.staff.module.network.API;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze;
import cn.aorise.petition.staff.module.network.entity.request.TAnalyze01;
import cn.aorise.petition.staff.module.network.entity.request.TJbDm;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyze;
import cn.aorise.petition.staff.module.network.entity.response.RDmMc;
import cn.aorise.petition.staff.ui.adapter.SpinnerAdapter;
import cn.aorise.petition.staff.ui.adapter.SpinnerStringAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/9.
 */

public class PetitionStaffAnalyzeShapeActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffActivityAnalyzeBinding mBinding;

    private BarChart barChart;// 柱形图控件
    private BarData data;// x轴数据
    private BarDataSet dataSet;// Y轴数据
    private String[] st={"案件总数","来信","来访","来电","网信"};

    private String[] x = new String[] { "来信", "来访", "来电","网信" };
    private Float[] y;

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
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_analyze);
        mBinding.startTime.setOnClickListener(this);
        mBinding.endTime.setOnClickListener(this);
        mBinding.llResponsible.setVisibility(View.GONE);
        mBinding.btnQuery.setOnClickListener(this);
        mBinding.barchart.setDragEnabled(true);
        mBinding.barchart.setScaleEnabled(true);
        mBinding.txtTitle.setText("信访形式数量");
        // barChart.invalidate();
        GetAnalyze(mBinding.startTime.getText().toString(), mBinding.endTime.getText().toString());

    }

    @Override
    protected void initEvent() {

    }



    private void GetAnalyze(String dataStart, String dataEnd) {
        TAnalyze01 t= new TAnalyze01();
        t.setDateStart(dataStart);
        t.setDateEnd(dataEnd);
        Subscription subscription = PetitionStaffApiService.Factory.create().GetAnalyze(GsonUtil.toJson(t))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RAnalyze>>() {
                        }.getType(), new APICallback<APIResult<List<Integer>>>() {
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
                            public void onNext(APIResult<List<Integer>> rAnalyzeAPIResult) {

                                try {
                                    ArrayList<String> xValues = new ArrayList<String>();
                                    String[] years = {"案件总数","来信","来访","来电","网信"};
                                    List<Integer> counts = rAnalyzeAPIResult.getData();
                                    for (int i = 0; i < years.length; i++) {
                                        xValues.add(years[i]);
                                    }
                                    ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
                                    for (int i = 0; i < counts.size(); i++) {
                                        /*float value = (float) (Math.random() * range*//*100以内的随机数*//*) + 3;*/
                                        yValues.add(new BarEntry(counts.get(i), i));
                                    }
                                    // y轴的数据集合
                                    BarDataSet barDataSet = new BarDataSet(yValues, "数量");
                                    barDataSet.setColor(getResources().getColor(R.color.bb_fdba4f));
                                    barDataSet.setValueFormatter(new ValueFormatter() {
                                        @Override
                                        public String getFormattedValue(float value, Entry entry,
                                                                        int dataSetIndex, ViewPortHandler viewPortHandler) {
                                            // TODO Auto-generated method stub
                                            BigDecimal b = new BigDecimal(value);
                                            int f1 = (int) b.setScale(1, BigDecimal.ROUND_HALF_UP)
                                                    .doubleValue();

                                            return f1 + "";
                                        }
                                    });
                                    ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
                                    barDataSets.add(barDataSet); // add the datasets

                                    BarData barData = new BarData(xValues, barDataSets);
                                    MpChartAndroidUtil.showBarChart1(PetitionStaffAnalyzeShapeActivity.this, mBinding.barchart, barData);
                                } catch (Exception e) {

                                }

                                /*饼状图*/

                                mBinding.piechart.animateXY(1400, 1400);
                                y= new Float[]{Float.parseFloat(rAnalyzeAPIResult.getData().get(1)+""),
                                        Float.parseFloat(rAnalyzeAPIResult.getData().get(2)+""),
                                        Float.parseFloat(rAnalyzeAPIResult.getData().get(3)+""),
                                        Float.parseFloat(rAnalyzeAPIResult.getData().get(4)+"")};
                                setPieData(x.length);
                            }

                            @Override
                            public void onMock(APIResult<List<Integer>> rAnalyzeAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }




    /*饼状图*/

    private void setPieData(int count) {
        mBinding.piechart.setDescription("");
        // 准备x"轴"数据：在i的位置，显示x[i]字符串
        ArrayList<String> xVals = new ArrayList<String>();

        // 真实的饼状图百分比分区。
        // Entry包含两个重要数据内容：position和该position的数值。
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int xi = 0; xi < count; xi++) {
            xVals.add(xi, x[xi]);

            // y[i]代表在x轴的i位置真实的百分比占
            yVals.add(new Entry(y[xi], xi));
        }

        PieDataSet yDataSet = new PieDataSet(yVals, "");

        // 每个百分比占区块绘制的不同颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(113, 168, 219));
        colors.add(Color.rgb(237, 125, 49));
        colors.add(Color.rgb(93, 208, 84));
        colors.add(Color.rgb(255, 192, 0));
        yDataSet.setColors(colors);

        // 将x轴和y轴设置给PieData作为数据源
        PieData data = new PieData(xVals, yDataSet);

        // 设置成PercentFormatter将追加%号
        data.setValueFormatter(new PercentFormatter());

        // 文字的颜色
        data.setValueTextColor(Color.BLACK);

        // 最终将全部完整的数据喂给PieChart
        mBinding.piechart.setData(data);
        mBinding.piechart.setDrawHoleEnabled(false);

        mBinding.piechart.setUsePercentValues(true);

        mBinding.piechart.setExtraOffsets(5, 10, 5, 5);

        mBinding.piechart.setDragDecelerationFrictionCoef(0.95f);



        //mBinding.piechart.setDrawHoleEnabled(true);
        mBinding.piechart.setHoleColor(Color.WHITE);

        mBinding.piechart.setTransparentCircleColor(Color.WHITE);
        mBinding.piechart.setTransparentCircleAlpha(110);

        mBinding.piechart.setHoleRadius(58f);
        mBinding.piechart.setTransparentCircleRadius(61f);

        mBinding.piechart.setDrawCenterText(true);

        mBinding.piechart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mBinding.piechart.setRotationEnabled(true);
        mBinding.piechart.setHighlightPerTapEnabled(true);
        mBinding.piechart.setDrawSliceText(false);//是否显示饼形图上的文字描述


        mBinding.piechart.invalidate();
    }

}
