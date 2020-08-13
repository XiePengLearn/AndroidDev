package com.xiaoanjujia.common.apiservice;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by $wu on 2019-08-09 上午 9:21.
 * 创建单例获取RetrofitService
 */

public class RetrofitServiceUtil {

    private static RetrofitServiceUtil instance;
    private static OkHttpClient        okHttpClient;

    private RetrofitServiceUtil() {
        //设置okhttp的响应式时间
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())             //添加拦截器未全局设置header
                .addInterceptor(new LogInterceptor());               //添加拦截器打印日志


    }

    private static RetrofitServiceUtil getInstance() {
        if (instance == null) {
            instance = new RetrofitServiceUtil();
        }
        return instance;
    }

    public static RetrofitService getRetrofitService() {
        return getInstance().innGetRetrofitService();
    }

    private RetrofitService innGetRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.18:8088/api/")//测试环境接口
                .baseUrl("http://14.29.175.35:8091/api/")//预生产环境接口
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(RetrofitService.class);
    }


}
