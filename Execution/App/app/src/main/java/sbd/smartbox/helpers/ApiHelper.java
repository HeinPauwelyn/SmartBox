package sbd.smartbox.helpers;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApiHelper {
    public static <T> void subscribe(rx.Observable<T> observable, Action1<T> action){
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Func1<Throwable, T>() {
            @Override
            public T call(Throwable throwable) {
                return null;
            }
        }).subscribe(action);
    }
}
