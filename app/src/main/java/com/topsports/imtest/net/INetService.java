package com.topsports.imtest.net;

import com.example.common.net.subscriber.BaseModel;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Date 2018/8/30
 * Time 13:54
 *
 * @author wentong.chen
 */
public interface INetService {
    String baseUrl = "https://app.netease.im/api/";

    /**
     * 注册
     * @param username
     * @param nickname
     * @param pwd
     * @return
     */
    @POST("createDemoUser")
    Flowable<BaseModel<String>> createUser(@Query("username") String username,
                                   @Query("nickname") String nickname,
                                   @Query("password") String pwd);


}
