package sbd.smartbox.helpers;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sbd.smartbox.api.SmartBoxService;

public class SmartboxApiHelper extends ApiHelper {

    private static Retrofit RETROFIT_INSTANCE;
    private static SmartBoxService SMARTBOX_SERVICE_INSTANCE;

    private static Object lock_retrofit = new Object();
    private static Object lock_service = new Object();

    public static Retrofit getRetrofitInstance() {
        if (RETROFIT_INSTANCE == null) {
            synchronized (lock_retrofit) {
                if (RETROFIT_INSTANCE == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

                    RETROFIT_INSTANCE = new Retrofit.Builder().baseUrl(Contract.SMARTBOX_BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                }
            }
        }
        return RETROFIT_INSTANCE;
    }

    public static SmartBoxService getSmartboxServiceInstance() {
        if (SMARTBOX_SERVICE_INSTANCE == null) {
            synchronized (lock_service) {
                if (SMARTBOX_SERVICE_INSTANCE == null) {
                    SMARTBOX_SERVICE_INSTANCE = getRetrofitInstance().create(SmartBoxService.class);
                }
            }
        }
        return SMARTBOX_SERVICE_INSTANCE;
    }

}
