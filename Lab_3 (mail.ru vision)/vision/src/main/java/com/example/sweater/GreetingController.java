package com.example.sweater;

import java.io.*;
import okhttp3.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.codecommit.model.File;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;

import java.util.HashMap;

@RestController
@RequestMapping("/vision")
class MainController {
    private final static String SERVICE = "https://smarty.mail.ru";
    private final static String DETECT = "/api/v1/objects/detect";
    private final static String TOKEN = "2DaKbabr7FJmtvKnGzTGH2QomauKq3vrBVAEMNENcDeLTFaRYq";
    private final static String FILE_NAME = "people.jpeg";
    private final static String PROVIDER = "mcs";

    MainController() {

    }

    private Request buildRequest(File file) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICE + DETECT).newBuilder();
        urlBuilder.addQueryParameter("oauth_provider", PROVIDER);
        urlBuilder.addQueryParameter("oauth_token", TOKEN);

        String url = urlBuilder.build().toString();

        okhttp3.RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("meta", "{\"mode\":[\"pedestrian\"],\"images\":[{\"name\":\"file_0\"}]}")
                .addFormDataPart("file_0", FILE_NAME, okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        return new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
    }

    @RequestMapping (value = "/recognize", method = RequestMethod.POST)
    String addPhoto(@RequestParam("file") MultipartFile file)
            throws IOException, InterruptedException {
        {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            OkHttpClient client = new OkHttpClient();
            Request request = buildRequest(convFile);
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Ошибка: " + response);
            }
            String resp = response.body().string();
            System.out.println(resp);
            return resp;
        }
    }
}