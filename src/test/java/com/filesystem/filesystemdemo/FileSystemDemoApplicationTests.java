package com.filesystem.filesystemdemo;
import static org.junit.Assert.*;
import io.restassured.internal.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import wiremock.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileSystemDemoApplicationTests {

    @Test
    public void contextLoads() {

    }



    @Test
    public void updateFile(){

        try{
        // load local file
        String filepath = System.getProperty("user.dir")+"/src/test/java/com/filesystem/filesystemdemo/220px-Blue_circled_9.svg.png";
        File file = new File(filepath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("220px-Blue_circled_9.svg.png",
                file.getName(), "image/png", IOUtils.toByteArray(input));//IOUtils.toByteArray(input)
        byte[] uploadedData = multipartFile.getBytes();

        // send post request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file",new FileSystemResource(file));
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = "http://0.0.0.0:8080/storage/documents/";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .postForEntity(serverUrl, requestEntity, String.class);

        String id = response.getBody();

        //download file
        RestTemplate restTemplate2 = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers2 = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<String>(headers2);

        ResponseEntity<byte[]> response2 = restTemplate2.exchange(
                serverUrl+id,
                HttpMethod.GET, entity, byte[].class, "1");

        if (response.getStatusCode() == HttpStatus.OK) {
            String path = System.getProperty("user.dir")+"/src/test/java/com/filesystem/filesystemdemo/downloadedfile.png";
            Files.write(Paths.get(path), response2.getBody());
        }

        //assertEquals
        File downloaded = new File(System.getProperty("user.dir")+"/src/test/java/com/filesystem/filesystemdemo/downloadedfile.png");
        FileInputStream input2 = new FileInputStream(downloaded);
        MultipartFile multipartFileDownloaded = new MockMultipartFile("DownloadED.png",
                file.getName(), "image/png", IOUtils.toByteArray(input));//IOUtils.toByteArray(input)
        byte[] downloadedData = multipartFile.getBytes();

        assertEquals(uploadedData,downloadedData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
