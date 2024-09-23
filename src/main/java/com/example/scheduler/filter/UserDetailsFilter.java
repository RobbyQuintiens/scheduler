package com.example.scheduler.filter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Component
public class UserDetailsFilter {

    public static final String AUTH_TOKEN = "Authorization";
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsFilter.class);

    public static String getUserInfo(HttpHeaders requestHeaders, String info) {
        if (requestHeaders.get(AUTH_TOKEN) != null) {
            List<String> header = requestHeaders.get(AUTH_TOKEN);
            assert header != null;
            String head = header.stream().findFirst().orElse("test");
            String authToken = head.replace("Bearer ", "");
            JSONObject jsonObj = decodeJWT(authToken);
            try {
                info = jsonObj.getString(info);
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        return info;
    }

    private static JSONObject decodeJWT(String jwtToken) {
        try {
            String[] splitString = jwtToken.split("\\.");
            if (splitString.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token format");
            }

            String base64EncodedBody = splitString[1];
            // Replace URL-safe characters
            String base64 = base64EncodedBody.replace('-', '+').replace('_', '/');
            // Add padding if necessary
            int padding = 4 - (base64.length() % 4);
            if (padding < 4) {
                base64 += "=".repeat(padding);
            }

            // Decode the Base64-encoded string
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            String body = new String(decodedBytes);
            return new JSONObject(body);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode JWT token", e);
        }
    }
}
