package kr.techdna.replatform2023.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView local(@RequestParam(required = false) String sigungu){
        ModelAndView mav = new ModelAndView();
        /* 메인 화면에서 지역별 설치현황 이동시 자동 페이지 로드 용 */
        if(sigungu != null) mav.addObject("sigungu", sigungu);
        mav.addObject("pageName", "local");
        mav.setViewName("local");
        return mav;
    }

    @RequestMapping("/business")
    public ModelAndView business(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageName", "business");
        mav.setViewName("business");
        return mav;
    }

    @RequestMapping("/map")
    public ModelAndView map(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageName", "map");
        mav.setViewName("map");
        return mav;
    }

    @RequestMapping("/as")
    public ModelAndView as(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageName", "as");
        mav.setViewName("as");
        return mav;
    }
}
