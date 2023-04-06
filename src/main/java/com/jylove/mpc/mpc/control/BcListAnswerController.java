package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.dto.BcListAnswerDto;
import com.jylove.mpc.mpc.dto.BcListDto;
import com.jylove.mpc.mpc.service.BcListAnswerService;
import com.jylove.mpc.mpc.service.BcListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BcListAnswerController {

    private final BcListAnswerService bcListAnswerService;

    private final BcListService bcListService;

    // 문의내역 답변 등록 페이지

    @GetMapping(value = "/new")
    public String bcListAnswerForm(Model model) {
        model.addAttribute("bcListAnswerDto", new BcListAnswerDto());
        return "BcListAnswer";
    }

    @GetMapping(value = "/bcList/list/{bcListAnswerId}") //답변하기 누를 시 상세페이지
    public String bcListDetail(@PathVariable("bcListAnswerId") Long bcListAnswerId, Principal principal, Model model) {
        try {
            String email = principal.getName();

            BcListDto bcListDto = bcListService.getBcListDtl(bcListAnswerId);
            BcListAnswerDto bcListAnswerDto = bcListAnswerService.createBcListAnswerDto();
            bcListAnswerDto.setAdminEmail(email);

            model.addAttribute("bcListDto", bcListDto);
            model.addAttribute("bcListAnswerDto", bcListAnswerDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "에러");
            return "BcListAnswer";
        }
        return "BcListAnswer";
    }

    @PostMapping(value = "/bcList/list/{bcListDtoId}") //답변 상세페이지에서 저장 누를 시
    public String bcListAnswer(@PathVariable("bcListDtoId") Long bcListDtoId, BcListAnswerDto bcListAnswerDto, Model model) {
        System.out.println(bcListAnswerDto.getAdminTitle());
        try {
            bcListAnswerService.saveBcListAnswer(bcListDtoId, bcListAnswerDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "저장 중 에러가 발생하였습니다.");
            return "BcListAnswer";
        }
        return "redirect:/bcList/show";
    }
}