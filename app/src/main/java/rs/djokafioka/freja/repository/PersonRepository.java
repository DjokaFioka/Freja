package rs.djokafioka.freja.repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.djokafioka.freja.network.api.APIService;
import rs.djokafioka.freja.network.api.RetrofitRequest;
import rs.djokafioka.freja.network.constants.AppConsts;
import rs.djokafioka.freja.network.response.ApiResponse;
import rs.djokafioka.freja.network.response.PersonResponse;
import rs.djokafioka.freja.network.response.ErrorResponse;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonRepository {
    private static final String TAG = "PersonRepository";
    private final APIService mAPIService;
    private ApiResponse<PersonResponse, ErrorResponse> mApiResponse;

    public PersonRepository() {
        mAPIService = RetrofitRequest.getInstance().create(APIService.class);
    }

    /**
     * Asynchronously send the request and notify {@code callback} of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     */
    public void getPersonListAsync(final OnDownloadingDataListener<ApiResponse<PersonResponse, ErrorResponse>> onDownloadingDataListener) {
        mAPIService.getPersonList(AppConsts.RELYING_PARTY_ID)
                .enqueue(new Callback<PersonResponse>() {
                    @Override
                    public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                        if (response.isSuccessful()) {
                            PersonResponse personResponse = new PersonResponse(response.body().getPersonList());
                            mApiResponse = new ApiResponse<>(personResponse);
                        }
                        else {
                            String errorMessage = "";
                            if (response.errorBody() != null) {
                                try {
                                    JSONObject json = new JSONObject(response.errorBody().string());
                                    errorMessage = json.getString("Message");
                                } catch (JSONException | IOException e) {
                                    errorMessage = e.getMessage();
                                    e.printStackTrace();
                                }
                            }
                            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
                            errorResponse.setResponseCode(response.code());
                            mApiResponse = new ApiResponse<>(errorResponse);
                        }
                        onDownloadingDataListener.onDownloadingDataCompleted(mApiResponse);
                    }

                    @Override
                    public void onFailure(Call<PersonResponse> call, Throwable t) {
                        ErrorResponse errorResponse = new ErrorResponse(t.getMessage());
                        mApiResponse = new ApiResponse<>(errorResponse);
                        onDownloadingDataListener.onDownloadingDataCompleted(mApiResponse);
                    }
                });
    }

    /**
     * Synchronously send the request and return its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     *
     */
    public ApiResponse<PersonResponse, ErrorResponse> getPersonList() {
        try
        {
            Response<PersonResponse> response = mAPIService.getPersonList(AppConsts.RELYING_PARTY_ID).execute();
            if (response.isSuccessful()) {
                PersonResponse personResponse = new PersonResponse(response.body().getPersonList());
                mApiResponse = new ApiResponse<>(personResponse);
            }
            else {
                String errorMessage = "";
                if (response.errorBody() != null) {
                    try {
                        JSONObject json = new JSONObject(response.errorBody().string());
                        errorMessage = json.getString("Message");
                    } catch (JSONException | IOException e) {
                        errorMessage = e.getMessage();
                        e.printStackTrace();
                    }
                }
                ErrorResponse errorResponse = new ErrorResponse(errorMessage);
                errorResponse.setResponseCode(response.code());
                mApiResponse = new ApiResponse<>(errorResponse);
            }
        } catch (IOException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            mApiResponse = new ApiResponse<>(errorResponse);
        }

        return mApiResponse;
    }

    public interface OnDownloadingDataListener<T>{
        void onDownloadingDataCompleted(T apiResponse);
    }
}
