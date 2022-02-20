package rs.djokafioka.freja.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import rs.djokafioka.freja.model.Person;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonResponse
{
    @SerializedName("personList")
    private List<Person> mPersonList;
    private Throwable mError;

    public PersonResponse(List<Person> personList)
    {
        mPersonList = personList;
        mError = null;
    }

    public PersonResponse(Throwable error)
    {
        mPersonList = null;
        mError = error;
    }

    public List<Person> getPersonList()
    {
        return mPersonList;
    }

    public void setPersonList(List<Person> personList)
    {
        mPersonList = personList;
    }

    public Throwable getError()
    {
        return mError;
    }

    public void setError(Throwable error)
    {
        mError = error;
    }
}
