package go.kr.dsp.api.app.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class ServerConnection {
    
    public boolean isServerConnected(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            
            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 400) {
                // 2xx and 3xx codes indicate successful responses
                log.info("응답 코드: {}", responseCode);
                return true;
            }
        } catch (Exception e) {
            log.error("연결 에러: {}", e.getMessage());
            
        }
        return false;
    }
}
