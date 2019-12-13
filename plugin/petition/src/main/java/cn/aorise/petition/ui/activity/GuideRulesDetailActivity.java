package cn.aorise.petition.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.R;
import cn.aorise.petition.common.Utils;
import cn.aorise.petition.databinding.PetitionGuideRulesDetailBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.request.TBH;
import cn.aorise.petition.module.network.entity.request.TQueryEvaluateDetail;
import cn.aorise.petition.module.network.entity.response.RGuideRulesDetail;
import cn.aorise.petition.ui.activity.htmltotxt.URLImageGetter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/16.
 */

public class GuideRulesDetailActivity extends PetitionBaseActivity implements View.OnClickListener{
    private static final String TAG = AboutActivity.class.getSimpleName();
    private PetitionGuideRulesDetailBinding mBinding;
    private String GGLX;
    private DisplayImageOptions options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader(GuideRulesDetailActivity.this);// TODO: 2017/10/9 初始化imagegetter
        GetGuideRulesDetail(getIntent().getStringExtra("BH"));
        GGLX = getIntent().getStringExtra("GGLX");
        System.out.println("GGLX:"+GGLX+"BH:"+getIntent().getStringExtra("BH"));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.petition_a)
                .showImageForEmptyUri(R.drawable.petition_a)
                .showImageOnFail(R.drawable.petition_a)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_guide_rules_detail);
        if (getIntent().getStringExtra("id").equals("1")){
            setToolBarTitle("信访资讯");
        }else if (getIntent().getStringExtra("id").equals("2")){

        }
    }

    @Override
    protected void initEvent() {

    }

    private void GetGuideRulesDetail (String BH) {
        TBH tQueryEvaluateDetail=new TBH();
        tQueryEvaluateDetail.setBH(BH);
        Subscription subscription = PetitionApiService.Factory.create().
                GetGuideRulesDetail(GsonUtil.toJson(tQueryEvaluateDetail))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_QUESTION_TYPE,
                        new TypeToken<APIResult<RGuideRulesDetail>>() {
                        }.getType(), new APICallback<APIResult<RGuideRulesDetail>>() {
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
                            public void onNext(APIResult<RGuideRulesDetail> rGuideRulesDetailAPIResult) {
                                System.out.println(rGuideRulesDetailAPIResult.getData().toString());
                                if ("3".equals(GGLX)) {
                                    mBinding.txtTitle.setText(rGuideRulesDetailAPIResult.getData().getBT());
                                    //mBinding.txtContent.setText("    "+rGuideRulesDetailAPIResult.getData().getNR());
                                    //mBinding.txtContent.setText(Html.fromHtml(rGuideRulesDetailAPIResult.getData().getNR(), new URLImageGetter(rGuideRulesDetailAPIResult.getData().getNR(), GuideRulesDetailActivity.this, mBinding.txtContent, options), null));
                                    mBinding.txtWebview.getSettings().setJavaScriptEnabled(true);//能够的调用JavaScript代码
                                    mBinding.txtWebview.loadDataWithBaseURL("", getNewContent(rGuideRulesDetailAPIResult.getData().getNR()), "text/html", "utf-8", "");
                                } else {
                                    mBinding.txtTitle.setText(rGuideRulesDetailAPIResult.getData().getBT());
                                    mBinding.txtContent.setText("    "+rGuideRulesDetailAPIResult.getData().getNR());
                                }
                            }

                            @Override
                            public void onMock(APIResult<RGuideRulesDetail> rGuideRulesDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(20 * 1024 * 1024); // 20 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static String getNewContent(String htmltext){ //html字符串中的图片进行适应屏幕宽度进行自适应缩放  加载到webview中
        /*try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            将每张图片都拉伸到屏幕宽度
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }*/
//
        try {//如果图片小于屏幕那么按照原始尺寸显示  如果大于屏幕则缩放到屏幕大小
            return "<html> \n" +
                    "<head> \n" +
                    "<style type=\"text/css\"> \n" +
                    "body {font-size:15px;color:#666666}\n" +
                    "img{max-width:100% !important;}\n" +
                    "</style> \n" +
                    "</head> \n" +
                    "<body width=100% style=word-wrap:break-word;>" +
                    htmltext +
                    "</body>" +
                    "</html>";
        } catch (Exception e) {
            return htmltext;
        }
    }
}
