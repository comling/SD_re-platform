package kr.techdna.replatform2023.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageName", "home");
        mav.setViewName("home");
        return mav;
    }

    @RequestMapping("/local")
    public ModelAndView local(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageName", "local");
        mav.setViewName("local");
        return mav;
    }

}
