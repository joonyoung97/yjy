package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.dto.BcListAnswerDto;
import com.jylove.mpc.mpc.dto.BcListDto;
import com.jylove.mpc.mpc.entity.BcList;
import com.jylove.mpc.mpc.service.BcListAnswerService;
import com.jylove.mpc.mpc.service.BcListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/bcList")
@RequiredArgsConstructor

public class BcListController {

    private final BcListService bcListService;
    private final BcListAnswerService bcListAnswerService;


    // 문의내역 등록 페이지

    @GetMapping(value = "/new")
    public String bcListForm(Principal principal, Model model){
        String email = principal.getName();
        BcListDto bcListDto = new BcListDto();
        bcListDto.setEmail(email);

        model.addAttribute("bcListDto", bcListDto);
        return "BcListForm";
    }


    @PostMapping(value = "/new")
    public String bcListNew(@Valid BcListDto bcListDto, BindingResult br, Model md, @RequestParam("bcListImgFile")MultipartFile bcListImgFile) {
        if (br.hasErrors()) {  // 문의내역 등록시 필수 입력값이 없을 시
            return "BcListForm";
        }try {
            bcListService.saveBcList(bcListDto, bcListImgFile);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("문의사항 등록 실패");
            md.addAttribute("errorMessage", "문의사항 등록 중 에러가 발생하였습니다.");
            return "BcListForm";
        }
        return "redirect:/bcList/show";
    }





    // 문의내역 첫 페이지 - 리스트

    @GetMapping(value = {"/show","/show/{page}"})
    public String pBcList(@PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        // 페이징을 위한 부분 현재 등록된 문의내역이 총 10개라면 페이징 수는 2페이지가 된다. 1페이지에 문의내역 5개씩 보여주게 설정함.
        // 5라는 숫자가 들어있는 부분이 한페이지에 표시할 공지사항 갯수이다.
        Page<BcList> bcLists = bcListService.getBcListPage(pageable);
        // 해당 페이지에 표시할 내용들과 페이징 정보

        model.addAttribute("bcListTotalCount", bcLists.getTotalElements());
        model.addAttribute("bcLists", bcLists);
        model.addAttribute("maxPage", 5);

        return "BcList";
    }

    @GetMapping(value = {"/user/show","/user/show/{page}"}) //현재 로그인한 계정의 문의내역 리스트
    public String userBcList(@PathVariable("page")Optional<Integer> page, Principal principal, Model model){
        String email = principal.getName(); //현재 로그인한 계정의 이메일 정보
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        // 페이징을 위한 부분 현재 등록된 문의내역이 총 10개라면 페이징 수는 2페이지가 된다. 1페이지에 문의내역 5개씩 보여주게 설정함.
        // 5라는 숫자가 들어있는 부분이 한페이지에 표시할 공지사항 갯수이다.
        Page<BcList> bcLists = bcListService.getUserBcListPage(email, pageable);
        // 해당 페이지에 표시할 내용들과 페이징 정보

        model.addAttribute("mybcList", "true");
        model.addAttribute("bcListTotalCount", bcLists.getTotalElements());
        model.addAttribute("bcLists", bcLists);
        model.addAttribute("maxPage", 5);

        return "BcList";
    }


    @GetMapping("/show/update/{bcListId}") //공지사항리스트에서 수정 버튼 눌렀을 시 상세페이지
    public String pUpdateBcList(@PathVariable("bcListId") Long bcListId, Model model){
        try {
            BcListDto bcListDto = bcListService.getBcListDtl(bcListId);
            model.addAttribute("bcListDto", bcListDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 공지사항이 없습니다.");
        }

        return "BcListForm";
    }



    @PostMapping("/show/update/{bcListId}") //공지사항 상세페이지에서 수정 실행
    public String pUpdateBcList(@Valid BcListDto bcListDto, BindingResult br,
                                @RequestParam("bcListImgFile") MultipartFile multipartFile, Model model){
        if(br.hasErrors()){
            return "BcListForm";
        }

        try{
            bcListService.updateBcList(bcListDto, multipartFile); //공지사항 수정 실행
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("문의사항 수정 실패");
            model.addAttribute("errorMessage", "문의사항 수정 중 에러가 발생하였습니다.");
            return "BcListForm";
        }

        return "redirect:/bcList/show";
    }



    @GetMapping("/show/delete/{bcListId}")
    public String pEnrollBcList(@PathVariable("bcListId") Long bcListId, Model model){
        try {
            bcListService.deleteBcList(bcListId);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("문의사항 삭제 실패");
            model.addAttribute("errorMessage", "문의사항 삭제 중 에러가 발생하였습니다.");
            return "BcListForm";
        }

        return "redirect:/bcList/show";
    }


    @GetMapping("/show/dtl/{bcListId}") //공지사항리스트에서 글 제목 눌렀을 때 상세 페이지
    public String bcListDetail(@PathVariable("bcListId") Long bcListId, Model model){
        try {
            BcListDto bcListDto = bcListService.getBcListDtl(bcListId);
            BcListAnswerDto bcListAnswerDto = bcListAnswerService.getBcListAnswerDtl(bcListId);
            model.addAttribute("bcListDto", bcListDto);
            model.addAttribute("bcListAnswerDto", bcListAnswerDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 공지사항이 없습니다.");
        }

        return "BcListDetail";
    }
}
