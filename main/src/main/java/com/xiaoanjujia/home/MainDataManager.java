package com.xiaoanjujia.home;

import com.google.gson.Gson;
import com.xiaoanjujia.common.model.BaseDataManager;
import com.xiaoanjujia.common.model.DataManager;
import com.xiaoanjujia.common.model.http.BaseApiService;
import com.xiaoanjujia.common.util.FillUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author：xp on 2018/4/20 18:26.
 */

public class MainDataManager extends BaseDataManager{

    public static String KPI_ROOT_URL = "http://14.29.175.35:8091/api";//预生产环境接口
    //通用模块路径
    public static String GENERAL_REGISTER = "/cm/v1";

    public MainDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static MainDataManager getInstance(DataManager dataManager){
        return new MainDataManager(dataManager);
    }

    /*
     *验证短信验证码注册/登陆 （只做示例，无数据返回）
     */
    public Disposable login(DisposableObserver<ResponseBody> consumer, String mobile, String verifyCode) {

        return changeIOToMainThread(getService(MainApiService.class).login(mobile,verifyCode), consumer);
    }


    public Disposable getMainData(int start , int count , DisposableObserver<ResponseBody> consumer){
        Map<String,Object> map = new HashMap<>(2);
        map.put("start",start);
        map.put("count",count);
        return changeIOToMainThread(getService(BaseApiService.class).executeGet("http://www.baidu.com",map),consumer);

    }

    public List<String> getTypeOfNameData(){
        ArrayList<String> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            list.add("家用电器");
        }
        return list;
    }

    public<S> Disposable getData(DisposableObserver<S> consumer , final Class<S> clazz , final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = getContext().getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }

    /**
     * 获取注册数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisterData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_REGISTER, mapParameters, mapHeaders), consumer);

    }
    /**
     * 获取验证码
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisretCodeData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_REGISTER, mapParameters, mapHeaders), consumer);

    }
    /**
     * 获取登录数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_REGISTER, mapParameters, mapHeaders), consumer);
    }
}
