package com.example.common.net.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author by chen.wentong on 2018/8/17.
 * 回调设置
 */

public abstract class BaseObjectSubscriber<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T obj) {
        onSuccess(obj);
    }

    @Override
    public void onError(Throwable t) {
        onFailure(t);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 成功回调
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调
     * @param throwable
     */
    public abstract void onFailure(Throwable throwable);
}
