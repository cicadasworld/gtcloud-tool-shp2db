package gtcloud.yqbjgh.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientHelper {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientHelper.class);

    public static final  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final OkHttpClient client = new OkHttpClient();

    public static void doHttpPostWithJsonBody(String endpoint, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(endpoint)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
        	LOG.info(json);
            if (!response.isSuccessful()) {
                int code = response.code();
                String emsg = String.format("restµ˜”√ ß∞‹: endpoint=%s, httpcode=%d, httpmsg=%s",
                        endpoint, code, response.message());
                throw new Exception(emsg);
            }
        } catch (Exception e) {
            LOG.error("«Î«Û ß∞‹: " + e.getMessage(), e);
        }
    }
}
