package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class WebServerController {

    HttpClient client;

    @Autowired
    public WebServerController(HttpClient client){
        this.client = client;
    }


    @RequestMapping(value = "/")
    public String homePage(){
        return "homepage";
    }

    @RequestMapping(value = "/admin/home")
    public String adminHome(){
        return "/admin/home";
    }

    @RequestMapping(value = "/user/home")
    public String userHome(){
        return "/user/home";
    }

    //Downloading the file
    @RequestMapping(value = "user/getfile/{ip}/{filename}")
    @ResponseBody
    public void getFile(@PathVariable("ip") String ip, @PathVariable("filename") String filename, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachement; filename=" + filename);
                response.setHeader("Content-Transfer-Encoding", "binary");

                try{
                    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                    ResponseEntity<ByteArrayResource> resp = client.getHTTPBytes(ip, "download?filename="+filename);

                    int code = resp.getStatusCode().value();
                    if (code != 200){
                        response.sendError(code);
                    }

                    InputStream is = resp.getBody().getInputStream();
                    //BufferedInputStream bs = new BufferedInputStream(is);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) > 0){
                        bos.write(buf, 0, len);
            }
            bos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/403")
        public String accessDenied(){
            return "accessDenied";
    }
}
