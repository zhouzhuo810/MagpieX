package me.zhouzhuo810.magpiex.utils;

import android.widget.EditText;

import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxHelper {
    
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @NonNull
            @Override
            public Observable<T> apply(@NonNull Observable<T> tObservable) {
                return tObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    
    public static <T> ObservableTransformer<T, T> io_io() {
        return new ObservableTransformer<T, T>() {
            @NonNull
            @Override
            public Observable<T> apply(@NonNull Observable<T> tObservable) {
                return tObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
            }
        };
    }
    
    /**
     * 倒计时
     *
     * @param count  时长（秒）
     * @param onNext 订阅
     * @return 用于取消订阅
     */
    public static Disposable countDown(long count, @NonNull Consumer<Long> onNext, Action onComplete) {
        return countDown(count, null, onNext, onComplete);
    }
    
    /**
     * 倒计时
     *
     * @param count      时长（秒）
     * @param onStart    开始订阅
     * @param onComplete 结束订阅
     * @param onNext     过程订阅
     * @return 用于取消订阅
     */
    public static Disposable countDown(long count, Consumer<Disposable> onStart, @NonNull Consumer<Long> onNext, Action onComplete) {
        Observable<Long> longObservable = Observable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
            .compose(RxHelper.<Long>io_main());
        if (onStart != null) {
            longObservable = longObservable.doOnSubscribe(onStart);
        }
        if (onComplete != null) {
            longObservable = longObservable.doOnComplete(onComplete);
        }
        return longObservable.subscribe(onNext, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }
    
    /**
     * 倒计时
     *
     * @param count      时长（秒）
     * @param onStart    开始订阅
     * @param onComplete 结束订阅
     * @param onNext     完成订阅
     * @param onError    报错订阅
     * @return 用于取消订阅
     */
    public static Disposable countDown(long count, Consumer<Disposable> onStart, @NonNull Consumer<Long> onNext, @NonNull Consumer<Throwable> onError, Action onComplete) {
        Observable<Long> longObservable = Observable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
            .compose(RxHelper.<Long>io_main());
        if (onStart != null) {
            longObservable = longObservable.doOnSubscribe(onStart);
        }
        if (onComplete != null) {
            longObservable = longObservable.doOnComplete(onComplete);
        }
        return longObservable.subscribe(onNext, onError);
    }
    
    /**
     * 定时器
     *
     * @param period   时长
     * @param timeUnit 单位
     * @param onNext   完成订阅
     * @return 用户取消订阅
     */
    public static Disposable timer(long period, TimeUnit timeUnit, @NonNull Consumer<Long> onNext) {
        return Observable.timer(period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(onNext, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }
    
    /**
     * 定时器
     *
     * @param period   时长
     * @param timeUnit 单位
     * @param onNext   完成订阅
     * @param onError  报错订阅
     * @return 用户取消订阅
     */
    public static Disposable timer(long period, TimeUnit timeUnit, @NonNull Consumer<Long> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.timer(period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(onNext, onError);
    }
    
    /**
     * 无限循环定时器
     *
     * @param period   时长
     * @param timeUnit 单位
     * @param consumer 完成订阅
     * @return 用户取消订阅
     */
    public static Disposable interval(long period, TimeUnit timeUnit, @NonNull Consumer<Long> consumer) {
        return Observable.interval(0, period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(consumer, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }
    
    /**
     * 无限循环定时器
     *
     * @param period   时长
     * @param timeUnit 单位
     * @param onNext   完成订阅
     * @param onError  报错订阅
     * @return 用户取消订阅
     */
    public static Disposable interval(long period, TimeUnit timeUnit, @NonNull Consumer<Long> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.interval(0, period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(onNext, onError);
    }
    
    /**
     * 无限循环定时器
     *
     * @param initialDelay 首次执行延时时长
     * @param period       时长
     * @param timeUnit     单位
     * @param onNext       完成订阅
     * @return 用户取消订阅
     */
    public static Disposable interval(long initialDelay, long period, TimeUnit timeUnit, @NonNull Consumer<Long> onNext) {
        return Observable.interval(initialDelay, period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(onNext, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                }
            });
    }
    
    /**
     * 无限循环定时器
     *
     * @param initialDelay 首次执行延时时长
     * @param period       时长
     * @param timeUnit     单位
     * @param onNext       完成订阅
     * @param onError      报错订阅
     * @return 用户取消订阅
     */
    public static Disposable interval(long initialDelay, long period, TimeUnit timeUnit, @NonNull Consumer<Long> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.interval(initialDelay, period, timeUnit)
            .compose(RxHelper.<Long>io_main())
            .subscribe(onNext, onError);
    }
    
    /**
     * 异步执行-String类型参数
     */
    public static <T> Disposable asyncTask(String params, @NonNull Function<? super String, ? extends T> mapper, @NonNull Consumer<T> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.just(params)
            .map(mapper)
            .compose(RxHelper.<T>io_main())
            .subscribe(onNext, onError);
    }
    
    /**
     * 异步执行-Long类型参数
     */
    public static <T> Disposable asyncTask(Long params, @NonNull Function<? super Long, ? extends T> mapper, @NonNull Consumer<T> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.just(params)
            .map(mapper)
            .compose(RxHelper.<T>io_main())
            .subscribe(onNext, onError);
    }
    
    /**
     * 异步执行-Integer参数
     */
    public static <T> Disposable asyncTask(Integer params, @NonNull Function<? super Integer, ? extends T> mapper, @NonNull Consumer<T> onNext, @NonNull Consumer<Throwable> onError) {
        return Observable.just(params)
            .map(mapper)
            .compose(RxHelper.<T>io_main())
            .subscribe(onNext, onError);
    }
    
    
    /**
     * 输入框回调频繁触发 onTextChanged 方法
     *
     * @param editText EditText
     * @param consumer Consumer<TextViewTextChangeEvent>
     * @return Disposable
     */
    public static Disposable debounce(EditText editText, Consumer<TextViewTextChangeEvent> consumer) {
        return debounce(editText, 200, consumer, throwable -> {});
    }
    
    /**
     * 输入框回调频繁触发 onTextChanged 方法
     *
     * @param editText  EditText
     * @param timeMills 间隔时间
     * @param consumer  Consumer<TextViewTextChangeEvent>
     * @param error     Consumer<Throwable>
     * @return Disposable
     */
    public static Disposable debounce(EditText editText, long timeMills, Consumer<TextViewTextChangeEvent> consumer, Consumer<Throwable> error) {
        return RxTextView.textChangeEvents(editText)
            .debounce(timeMills, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.BUFFER)
            .subscribe(consumer, error);
    }
    
}
