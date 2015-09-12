package transitobjects;

/**
 * Created by LocalEvan on 2015-07-22.
 */
public class JsonResponse {
    private String status = "";
    private String errorMessage = "";

    public JsonResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
