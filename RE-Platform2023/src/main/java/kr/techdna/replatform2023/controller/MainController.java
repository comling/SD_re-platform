package kr.techdna.replatform2023.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    public ModelAndView map(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String BNAME,
                            @RequestParam(required = false) Integer BYEAR,
                            @RequestParam(required = false) String energy,
                            @RequestParam(required = false) String facilityType,
                            @RequestParam(required = false) String sigungu){
        ModelAndView mav = new ModelAndView();

        if(keyword != null ) mav.addObject("keyword", keyword);
        if(BNAME != null ) mav.addObject("BNAME", BNAME);
        if(BYEAR != null ) mav.addObject("BYEAR", BYEAR);
        if(energy != null ) mav.addObject("energy", energy);
        if(facilityType != null ) mav.addObject("facilityType", facilityType);
        if(sigungu != null ) mav.addObject("sigungu", sigungu);

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
