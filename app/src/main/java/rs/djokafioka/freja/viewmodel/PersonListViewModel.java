package rs.djokafioka.freja.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import rs.djokafioka.freja.network.response.PersonResponse;
import rs.djokafioka.freja.repository.PersonRepository;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonListViewModel extends ViewModel
{
    private PersonRepository mPersonRepository;
    private MutableLiveData<PersonResponse> mPersonListLiveData;

    public PersonListViewModel()
    {
        mPersonRepository = new PersonRepository();
        //mPersonListLiveData = mPersonRepository.getPersonList();
    }

    public MutableLiveData<PersonResponse> getPersonListObserver()
    {
        if (mPersonListLiveData == null)
        {
            mPersonListLiveData = new MutableLiveData<PersonResponse>();
            loadData();
        }
        return mPersonListLiveData;
    }

    public void loadData()
    {
        mPersonListLiveData = mPersonRepository.getPersonList();
    }
}
