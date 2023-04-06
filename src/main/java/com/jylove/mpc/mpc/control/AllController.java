package com.jylove.mpc.mpc.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AllController {
    @GetMapping("/SUB_Service")
    public String SUB() {
        return "user/SUB_Service";
    }

    @GetMapping("/wordCup")
    public String wrod(){
        return "WordCup";
    }
}
