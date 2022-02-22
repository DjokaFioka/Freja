package rs.djokafioka.freja.repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.djokafioka.freja.network.api.APIService;
import rs.djokafioka.freja.network.api.RetrofitRequest;
import rs.djokafioka.freja.network.constants.AppConsts;
import rs.djokafioka.freja.network.response.PersonResponse;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonRepository {
    private static final String TAG = "PersonRepository";
    private final APIService mAPIService;

    public PersonRepository() {
        mAPIService = RetrofitRequest.getInstance().create(APIService.class);
    }

    public MutableLiveData<PersonResponse> getPersonList() {
        final MutableLiveData<PersonResponse> liveData = new MutableLiveData<>();
        mAPIService.getPersonList(AppConsts.RELYING_PARTY_ID)
                .enqueue(new Callback<PersonResponse>() {
                    @Override
                    public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                        if (response.isSuccessful()) {
                            liveData.postValue(response.body());
                        }
                        else {
                            //Response is not 200-299, so do something about it
                            String errorMessage = "HttpResponseCode = " + response.code();
                            if (response.errorBody() != null) {
                                try {
                                    JSONObject json = new JSONObject(response.errorBody().string());
                                    errorMessage += ", with message: " + json.getString("Message");
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            Throwable throwable = new Throwable(errorMessage);
                            liveData.postValue(new PersonResponse(throwable));
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonResponse> call, Throwable t) {
                        liveData.postValue(new PersonResponse(t));
                    }
                });

        return liveData;
    }
}
