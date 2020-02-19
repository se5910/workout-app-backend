package ucmo.workoutapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello(
            @RequestParam(defaultValue = "world", required = false)String name, Model model){
        model.addAttribute("user",name);

        return "hello";
    }
}
