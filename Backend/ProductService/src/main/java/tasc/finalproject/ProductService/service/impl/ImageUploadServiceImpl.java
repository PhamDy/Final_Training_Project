package tasc.finalproject.ProductService.service.impl;

import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tasc.finalproject.ProductService.service.ImageUploadService;


@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Override
    public String uploadImage(MultipartFile file) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.imgur.com/3/image");

        try {
            httpPost.addHeader("Authorization", "Client-ID 941cc9daaaeec01");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody("image", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                return extractImageUrl(responseString);
            }
        } catch (IOException | java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException | java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String extractImageUrl(String responseJson) {
        try {
            JsonObject jsonObject = JsonParser.parseString(responseJson).getAsJsonObject();
            JsonObject dataObject = jsonObject.getAsJsonObject("data");
            return dataObject.get("link").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
