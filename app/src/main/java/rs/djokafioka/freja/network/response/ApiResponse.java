package rs.djokafioka.freja.network.response;

/**
 * Created by Djordje on 22.2.2022..
 */
public class ApiResponse <S, F extends ErrorResponse>{

    private S mSuccess;
    private F mFailure;

    public ApiResponse(S success) {
        mSuccess = success;
        mFailure = null;
    }

    public ApiResponse(F failure) {
        mSuccess = null;
        mFailure = failure;
    }

    public S getSuccess() {
        return mSuccess;
    }

    public F getFailure() {
        return mFailure;
    }

    public boolean isSuccess() {
        return mSuccess != null;
    }
}
