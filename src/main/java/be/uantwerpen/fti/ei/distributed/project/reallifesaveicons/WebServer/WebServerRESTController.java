package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class WebServerRESTController {

    String namingIP = "localhost";
    HttpClient client;

    @Autowired
    public WebServerRESTController(HttpClient client) {
        this.client = client;
    }

    @RequestMapping(value = "user/location", method = RequestMethod.GET)
    public ResponseEntity<String> findFile(@RequestParam(value = "filename") String fileName) {
        return client.getHTTPString(namingIP, "/fileLocation?filename=" + fileName);
    }

    @RequestMapping(value = "user/exists", method = RequestMethod.GET)
    public ResponseEntity findFile(@RequestParam(value = "ip") String ip, @RequestParam(value = "filename") String fileName) {

        List<List<String>> responseList = client.getHTTPLists(ip, "/getfiles");

        for (List<String> response : responseList) {
            if (response.contains(fileName)) return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "admin/nodes", method = RequestMethod.GET)
    public ResponseEntity<String> getNodes() {

        return client.getHTTPString(namingIP, "/allnodes");
    }

    @RequestMapping(value = "admin/allfiles", method = RequestMethod.GET)
    public ResponseEntity<List<List<String>>> allfiles(@RequestParam(value = "ip") String ip){

        List<List<String>> response = client.getHTTPLists(ip, "/getfiles");

        //Check if there are files
        boolean allEmpty = true;
        for (List subList : response){
            if (!subList.isEmpty()) allEmpty = false;
        }

        if (allEmpty){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "admin/shutdown", method = RequestMethod.GET)
    public ResponseEntity shutdown(@RequestParam(value = "ip") String nodeip){
        System.out.println(nodeip);
        client.putHTTP(nodeip, "/shutdown");
        return new ResponseEntity(HttpStatus.OK);
    }

}
