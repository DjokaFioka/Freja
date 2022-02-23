package rs.djokafioka.freja.network.response;

/**
 * Created by Djordje on 22.2.2022..
 */
public class ErrorResponse
{

    private String mErrorMessage;
    private int mResponseCode;

    public ErrorResponse(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(int responseCode) {
        mResponseCode = responseCode;
    }
}
