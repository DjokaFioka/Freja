package rs.djokafioka.freja.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Djordje on 18.2.2022..
 */
public class Person
{
    @SerializedName("firstName")
    private String mFirstName;

    @SerializedName("lastName")
    private String mLastName;

    @SerializedName("base64Photo")
    private String mBase64Photo;

    public Person(String firstName, String lastName, String base64Photo)
    {
        mFirstName = firstName;
        mLastName = lastName;
        mBase64Photo = base64Photo;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName(String firstName)
    {
        mFirstName = firstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName(String lastName)
    {
        mLastName = lastName;
    }

    public String getBase64Photo()
    {
        return mBase64Photo;
    }

    public void setBase64Photo(String base64Photo)
    {
        mBase64Photo = base64Photo;
    }

    public String getName()
    {
        return mFirstName + " " + mLastName;
    }
}
