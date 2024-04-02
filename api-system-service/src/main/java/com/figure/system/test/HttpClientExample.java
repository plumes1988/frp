package com.figure.system.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Date;

public class HttpClientExample {
    public static void main(String[] args) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        Date start = new Date();
        HttpGet httpGet = new HttpGet("http://192.168.100.191:8084/health");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
            System.out.println("Response Body: " + responseBody);
            Date end = new Date();
            System.out.println(end.getTime()-start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
