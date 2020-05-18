package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class HttpClient{

    //Logger
    private final RestTemplate restTemplate;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> getHTTPString(String ip, String data){
        String url = "http://" + ip + ":8081/" + data;
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<ByteArrayResource> getHTTPBytes(String ip, String data){
        String url = "http://" + ip + ":8082/" + data;
        return restTemplate.getForEntity(url, ByteArrayResource.class);
    }

    public List<List<String>> getHTTPLists(String ip, String data){
        String url = "http://" + ip + ":8082/" + data;
        return restTemplate.getForObject(url, List.class);
    }
}
