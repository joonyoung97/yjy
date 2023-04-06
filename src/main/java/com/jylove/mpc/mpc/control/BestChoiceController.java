package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdacFormDto;
import com.jylove.mpc.mpc.dto.BestChoiceDto;
import com.jylove.mpc.mpc.entity.BCmatching;
import com.jylove.mpc.mpc.entity.Notice;
import com.jylove.mpc.mpc.service.AdAcPageService;
import com.jylove.mpc.mpc.service.BestChoiceService;
import com.jylove.mpc.mpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BestChoiceController {

    private final AdAcPageService adAcPageService;
    private final BestChoiceService bestChoiceService;
    private final MemberService memberService;

    @GetMapping("/bestchoice") //사용자가 추천을 눌렀을 시
    public String bestChoice(Principal principal, Model model){
        String email = principal.getName(); //현재 로그인한 계정의 이메일 정보

        try {
            List<AdacFormDto> bestChoiceList = bestChoiceService.randomChoice(email);
            model.addAttribute("bestChoiceList", bestChoiceList);
        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "추천 페이지 로딩 중 에러가 발생하였습니다.");
            return "redirect:/";
        }

        return "user/BestChoice";
    }

    @GetMapping("/bestchoice/bcdetail/{bcId}") //추천에서 매칭 된 유기견,유기묘를 누를 시
    public String bcDetail(@PathVariable("bcId") Long bcId, Model model){
        try {
            AdacFormDto adacFormDto = bestChoiceService.getBestChoiceDtl(bcId);
            String mbti = String.valueOf(adacFormDto.getMbti());
            model.addAttribute("adacFormDto", adacFormDto);
            model.addAttribute("mbti", mbti);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "매칭된 항목을 찾는 중 에러가 발생하였습니다.");
            return "redirect:/bestchoice";
        }

        return "user/BC_Detail";
    }

    @PostMapping("/bestchoice/matching") //매칭요청 누를 시
    public String bcMatching(Principal principal, @Valid AdacFormDto adacFormDto, BindingResult br, Model model){
        try {
            String email = principal.getName(); //현재 로그인한 계정의 이메일

            if(bestChoiceService.checkMatching(email, adacFormDto)){
                model.addAttribute("checkMatching", "true"); //이미 매칭이 되어있는 동물일 경우 true
                return "user/BC_Detail";
            }
            else {
                bestChoiceService.saveBcMatching(email, adacFormDto);
                AdAcPageDto adAcPageDto = adAcPageService.getAdAcPageDetail(adacFormDto.getAdAcPage().getId());
                model.addAttribute("adAcPageDto", adAcPageDto);
            }
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "매칭요청 중 에러가 발생하였습니다.");
            return "redirect:/bestchoice";
        }

        return "user/BC_Matching";
    }

    @GetMapping(value = {"/bestchoice/list", "/bestchoice/list/{page}"})
    public String bcList(Principal principal, @PathVariable("page") Optional<Integer> page, Model model){
        String email = principal.getName(); //현재 로그인된 계정의 이메일 정보
        Long id = memberService.findemail(email); //현재 로그인된 계정의 DB ID값

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        //페이징을 위한 부분 현재 등록된 공지사항이 총 10개라면 페이징 수는 4페이지가 된다. 1페이지에 공지사항 3개씩 보여주게 설정했다.
        //3 이라는 숫자가 들어있는 부분이 한페이지에 표시할 공지사항 갯수이다.
        Page<BCmatching> bCmatchings = bestChoiceService.getBcMatchingPage(id, pageable);
        //해당 페이지에 표시할 내용들과 페이징 정보

        List<AdAcPageDto> adAcPageDtoList = adAcPageService.getAdacPageMatching(bCmatchings);
        List<BestChoiceDto> bestChoiceDtoList = bestChoiceService.getBestChoiceMatching(bCmatchings);

        model.addAttribute("bestChoiceDtoList", bestChoiceDtoList);
        model.addAttribute("adAcPageDtoList", adAcPageDtoList);
        model.addAttribute("bCmatchingTotalCount", bCmatchings.getTotalElements()); //공지사항 순번을 위한 데이터 갯수
        model.addAttribute("bCmatchings", bCmatchings);
        model.addAttribute("maxPage", 5);

        return "user/BC_MatchingList";
    }
}
