package rs.djokafioka.freja.viewmodel;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import rs.djokafioka.freja.model.Person;
import rs.djokafioka.freja.network.response.ApiResponse;
import rs.djokafioka.freja.network.response.PersonResponse;
import rs.djokafioka.freja.network.response.ErrorResponse;
import rs.djokafioka.freja.repository.PersonRepository;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonListViewModel extends ViewModel {
    private PersonRepository mPersonRepository;
    private MutableLiveData<ApiResponse<PersonResponse, ErrorResponse>> mApiResponseLiveData = new MutableLiveData<>();

    public PersonListViewModel() {
        mPersonRepository = new PersonRepository();
    }

    public MutableLiveData<ApiResponse<PersonResponse, ErrorResponse>> getPersonListObserver() {
        return mApiResponseLiveData;
    }

    public void downloadData() {
        mPersonRepository.getPersonListAsync(new PersonRepository.OnDownloadingDataListener<ApiResponse<PersonResponse, ErrorResponse>>() {
            @Override
            public void onDownloadingDataCompleted(ApiResponse<PersonResponse, ErrorResponse> apiResponse) {
                if (apiResponse.isSuccess()){
                    Collections.sort(apiResponse.getSuccess().getPersonList(), new Comparator<Person>() {
                        @Override
                        public int compare(Person o1, Person o2) {
                            return (o1.getLastName().compareTo(o2.getLastName()));
                        }
                    });
                }
                mApiResponseLiveData.postValue(apiResponse);
            }
        });

    }

    public void downloadDataUsingThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApiResponse<PersonResponse, ErrorResponse> apiResponse = mPersonRepository.getPersonList();
                if (apiResponse.isSuccess()) {
                    Collections.sort(apiResponse.getSuccess().getPersonList(), new Comparator<Person>() {
                        @Override
                        public int compare(Person o1, Person o2) {
                            return (o1.getLastName().compareTo(o2.getLastName()));
                        }
                    });
                }
                mApiResponseLiveData.postValue(apiResponse);
            }
        });
        thread.start();

    }
}
