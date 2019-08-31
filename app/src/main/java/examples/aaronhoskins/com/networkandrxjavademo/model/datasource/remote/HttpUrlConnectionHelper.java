package examples.aaronhoskins.com.networkandrxjavademo.model.datasource.remote;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnectionHelper {
    public static final String COMPLETE_URL = "https://randomuser.me/api/?results=5";
    private static HttpURLConnection httpURLConnection;
    private static URL url;

    public static void getRandomUsers(HttpCallback callback) throws IOException {
        String jsonResponse = "";
        url = new URL(COMPLETE_URL);
        httpURLConnection = (HttpURLConnection)url.openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();

        int currentRead = inputStream.read();
        while(currentRead != -1) {
            char currentChar = (char)currentRead;
            jsonResponse = jsonResponse + currentChar;
            currentRead = inputStream.read();
        }
        callback.onHttpUrlConnectionResponse(jsonResponse);
    }

    public interface HttpCallback{
        void onHttpUrlConnectionResponse(String json);
    }
}


