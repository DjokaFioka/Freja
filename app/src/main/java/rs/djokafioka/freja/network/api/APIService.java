package rs.djokafioka.freja.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rs.djokafioka.freja.network.response.PersonResponse;

/**
 * Created by Djordje on 18.2.2022..
 */
public interface APIService
{
    @GET("logo")
    Call<PersonResponse> getPersonList(@Query("relyingPartyId") String relyingPartyId);
}
