package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdAcPageSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.service.AdAcPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/adacpage")
@RequiredArgsConstructor
public class AdAcPageController {

    private final AdAcPageService adAcPageService;

    @GetMapping(value = "/adacpageForm") //유기센터 등록 버튼 누를 시 등록페이지
    public  String adacpage(Model model) {
        AdAcPageDto adAcPageDto = new AdAcPageDto();
        model.addAttribute("adAcPageDto",adAcPageDto);
        return "adacpageForm";
    }


    @PostMapping(value = "/new") //유기센터 등록페이지에서 저장 버튼 누를 시
    public String home(@Valid AdAcPageDto adAcPageDto, BindingResult br, Model md) {
        if (br.hasErrors()) {
            return "adacpageForm";
        }try {
            AdAcPage adAcPage = AdAcPage.create(adAcPageDto);
            adAcPageService.saveadacpage(adAcPage);
        }catch (Exception e) {
            md.addAttribute("errorMessage", e.getMessage());
            return "adacpageForm";
        }
        return "redirect:/adacpage/show";
    }


    @GetMapping(value = {"/show","/show/{page}"}) //등록된 센터 리스트, 페이징
    public String show( AdAcPageSearchDto adAcPageSearchDto,@PathVariable("page") Optional<Integer> page, Model model){  // 매개변수 순서는 상관없음  //페이징을 하여 화면에 보여주기 위한 것

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 5);
        Page<AdAcPage> adAcPages  = adAcPageService.getAdminItemPage(adAcPageSearchDto,pageable);   // 검색

        model.addAttribute("adAcPageCount", adAcPages.getTotalElements());
        model.addAttribute("adAcPages",adAcPages);
        model.addAttribute("adAcPageSearchDto", adAcPageSearchDto);   // 검색
        model.addAttribute("maxPage",5);


        return "adacpageList";  // 템플릿 안에 있는 경로에는 '/' 이 문자를 입력 안해줘도 된다.
    }




    @GetMapping("/show/update/{AdAcPageId}") //유기견 관리 센터 리스트에서 수정 버튼 눌렀을 시 상세페이지
    public String adAcPageUpdate(@PathVariable("AdAcPageId") Long AdAcPageId, Model model){
        try {
            AdAcPageDto adAcPageDto = adAcPageService.getAdAcPageDetail(AdAcPageId);
            model.addAttribute("adAcPageDto", adAcPageDto);
        }catch (EntityNotFoundException e){
//            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 센터가 없습니다.");
            model.addAttribute("adAcPageDto",new AdAcPageDto());
            return "adacpageForm";
        }

        return "adacpageForm";
    }


    @PostMapping(value = "/show/update/{AdAcPageId}") //상세페이지에서 수정 누를 시 수정실행
    public String adAcPageUpdate(@Valid AdAcPageDto adAcPageDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "adacpageForm";
        }

        try {
            adAcPageService.updateAdacpage(adAcPageDto); //수정 실행
        }catch (Exception e){
            model.addAttribute("errorMessage", "센터 수정 중 에러가 발생하였습니다.");
            return "adacpageForm";
        }

        return "redirect:/adacpage/show";
    }



    @GetMapping("/show/delete/{AdAcPageId}") //삭제
    public String adAcPageDelete(@PathVariable("AdAcPageId") Long AdAcPageId, Model model){
        try {
            adAcPageService.deleteAdAcPage(AdAcPageId);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("센터 정보 삭제 실패");
            model.addAttribute("errorMessage", "유기센터 삭제 중 에러가 발생하였습니다.");
            return "adacpageForm";
        }

        return "redirect:/adacpage/show";
    }
}
