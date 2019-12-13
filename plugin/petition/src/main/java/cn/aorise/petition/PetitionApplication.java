package cn.aorise.petition;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.interfaces.IAppCycle;
import cn.aorise.common.core.utils.assist.DebugUtil;
import cn.aorise.petition.config.Config;
import cn.aorise.petition.ui.bean.Petition_contact_people;

public class PetitionApplication extends Application implements IAppCycle {
    private static final String TAG = PetitionApplication.class.getSimpleName();
    /*测试22*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void create(Application context) {
        Log.i(TAG, "init");
        // AoriseLog.init(DebugUtil.isDebug(), BuildConfig.APPLICATION_ID);
        // DbHelper.getInstance().init(context);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();//在安卓7.0以上需要设置这句才可以获得uri不然会报空指针不能操作本地文件
        //或者 添加fileprovide
        DebugUtil.setDebug(Config.sIsDebug);
    }

    @Override
    public void destroy(Application context, boolean isKillProcess) {
        // DbHelper.getInstance().close();
    }


}
