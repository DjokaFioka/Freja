package rs.djokafioka.freja.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.djokafioka.freja.network.constants.AppConsts;

/**
 * Created by Djordje on 18.2.2022..
 */
public class RetrofitRequest
{
    private static Retrofit sRetrofit;

    public static Retrofit getInstance()
    {
        if(sRetrofit == null)
        {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConsts.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
