package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class HttpClient {

    //Logger
    private final RestTemplate restTemplate;

    public HttpClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /*public void putHTTP(String ip, String data){
        String url = "http://" + ip + ":8081/" + data;
        restTemplate.put(url, String.class);
    }*/

    public ResponseEntity<String> getHTTP(String ip, String data){
        String url = "http://" + ip + ":8081/" + data;
        return restTemplate.getForEntity(url, String.class);
    }
}
