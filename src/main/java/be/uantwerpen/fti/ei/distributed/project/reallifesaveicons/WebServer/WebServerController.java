package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class WebServerController {

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

    @RequestMapping(value = "/403")
        public String accessDenied(){
            return "accessDenied";
    }
}
