package com.example.archvizarena.web.testTemplatesController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestTemplatesController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

//    @GetMapping
//    public String home(){
//        return "index";
//    }

    @GetMapping("/users/login")
    public String getLogin(){
        return "auth-login";
    }

    @GetMapping("/users/register")
    public String getRegister(){
        return "register-role-chose";
    }

    @GetMapping("/users/register/artist")
    public String getRegisterArtist(){
        return "register-artist";
    }

    @GetMapping("/users/register/buyer")
    public String getRegisterBuyer(){
        return "register-buyer";
    }

    @GetMapping("/projects/add")
    public String getAddProject(){
        return "project-add";
    }

    @GetMapping("/jobs/add")
    public String getAddJob(){
        return "job-add";
    }
}
