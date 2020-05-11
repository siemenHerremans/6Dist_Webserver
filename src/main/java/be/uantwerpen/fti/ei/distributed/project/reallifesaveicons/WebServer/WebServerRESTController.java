package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebServerRESTController {

    HttpClient httpClient;
    String namingIP = "localhost";

    @Autowired
    public WebServerRESTController(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    @RequestMapping(value = "user/location", method = RequestMethod.GET)
    public ResponseEntity<String> findFile(@RequestParam(value = "filename") String fileName){

        return httpClient.getHTTP(namingIP, "/fileLocation?filename="+fileName);
    }

    @RequestMapping(value = "admin/nodes", method = RequestMethod.GET)
    public ResponseEntity<String> getNodes(){

        return httpClient.getHTTP(namingIP, "/allnodes");
    }
}
