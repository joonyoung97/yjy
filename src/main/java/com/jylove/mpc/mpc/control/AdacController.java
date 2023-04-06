package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.constant.*;
import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdacFormDto;
import com.jylove.mpc.mpc.dto.AdacSearchDto;
import com.jylove.mpc.mpc.dto.NoticeSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.BestChoice;
import com.jylove.mpc.mpc.entity.Notice;
import com.jylove.mpc.mpc.service.AdAcPageService;
import com.jylove.mpc.mpc.service.AdacService;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdacController {

    private final AdAcPageService adAcPageService;
    private final AdacService adacService;

    @GetMapping(value = {"/adac", "/adac/{page}"}) //유기견관리 전체 뷰페이지, 페이지 번호에 따른 뷰페이지
    public String adac(AdacSearchDto adacSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        //페이징을 위한 부분 현재 등록된 유기견이 총 20마리라면 페이징 수는 2페이지가 된다. 1페이지에 유기견 10마리씩 보여주게 설정했다.
        Page<BestChoice> bestChoices = adacService.getAdminAdacPage(adacSearchDto, pageable);
        //해당 페이지에 표시할 내용들과 페이징 정보

        List<AdAcPageDto> adAcPageList = adacService.getAdacPage(bestChoices);
        //등록된 유기견에 매칭된 센터를 페이지에 보여주기 위해 DB조회 후 리스트로 담았다.

        model.addAttribute("adacpageDtos", adAcPageService.getAdacPageAll());
        model.addAttribute("bestChoiceTotalCount", bestChoices.getTotalElements()); //공지사항 순번을 위한 데이터 갯수
        model.addAttribute("adAcPageList", adAcPageList);
        model.addAttribute("bestChoices", bestChoices);
        model.addAttribute("maxPage", 5);

        return "admin/AdacList";
    }

    @GetMapping("/adac/new") //등록 버튼 누를 시 등록 페이지
    public String adacNew(Model model){
        model.addAttribute("adacpageDtos", adAcPageService.getAdacPageAll()); //등록된 센터를 보여주기 위한 센터리스트
        model.addAttribute("adacFormDto", new AdacFormDto());

        return "admin/AdacForm";
    }

    @ModelAttribute("ages") // 나이 선택용 ENUM 객체 생성
    public Age[] ages() {
        return Age.values();
    }
    @ModelAttribute("animals") // 강아지,고양이 선택용 ENUM 객체 생성
    public Animal[] Animals() {
        return Animal.values();
    }
    @ModelAttribute("cultivars") // 품종 선택용 ENUM 객체 생성
    public Cultivar[] cultivars() {
        return Cultivar.values();
    }
    @ModelAttribute("natures") // 성격 선택용 ENUM 객체 생성
    public Nature[] natures() {
        return Nature.values();
    }
    @ModelAttribute("sizes") // 사이즈 선택용 ENUM 객체 생성
    public Size[] sizes() {
        return Size.values();
    }
    @ModelAttribute("vaccinations") // 예방접종 선택용 ENUM 객체 생성
    public Vaccination[] vaccinations() {
        return Vaccination.values();
    }

    @PostMapping("/adac/new") //저장 버튼 누를 시
    public String adacNew(@Valid AdacFormDto adacFormDto, BindingResult br,
                          @RequestParam("adacVideoFile") MultipartFile adacVideoFile,
                          @RequestParam("adacImgFile") List<MultipartFile> adacImgFileList, Model model){
        model.addAttribute("adacpageDtos", adAcPageService.getAdacPageAll());

        if(br.hasErrors()){
            return "admin/AdacForm";
        }

        if(adacVideoFile.isEmpty()){ //영상은 필수로 첨부하도록
            model.addAttribute("errorMessage", "영상 첨부는 필수입니다.");
            return "admin/AdacForm";
        }
        else if(adacImgFileList.get(0).isEmpty()){ //첫번째 이미지는 필수로 첨부하도록
            model.addAttribute("errorMessage", "첫번째 이미지는 필수입니다.");
            return "admin/AdacForm";
        }

        try {
            adacService.saveAdac(adacFormDto, adacVideoFile, adacImgFileList); //저장 실행
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "유기견 등록 중 에러가 발생하였습니다.");
            return "admin/AdacForm";
        }

        return "redirect:/admin/adac";
    }

    @GetMapping("/adac/update/{adacId}") //수정 버튼 누를 시 상세 페이지
    public String adacUpdate(@PathVariable("adacId") Long adacId, Model model){
        try {
            AdacFormDto adacFormDto = adacService.getAdacDtl(adacId); //id에 해당하는 정보를 DB에서 조회하여 Dto에 담아줌
            model.addAttribute("adacpageDtos", adAcPageService.getAdacPageAll());
            model.addAttribute("adacFormDto", adacFormDto);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 유기견이 없습니다.");
        }


        return "admin/AdacForm";
    }

    @PostMapping("/adac/update/{adacId}") //상세페이지에서 수정 실행
    public String adacUpdate(@Valid AdacFormDto adacFormDto, BindingResult br,
                             @RequestParam("adacVideoFile") MultipartFile adacVideoFile,
                             @RequestParam("adacImgFile") List<MultipartFile> adacImgFileList, Model model){
        if(br.hasErrors()){
            return "admin/AdacForm";
        }

        try{
            adacService.updateAdac(adacFormDto, adacVideoFile, adacImgFileList); //수정 실행
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "유기견 수정 중 에러가 발생하였습니다.");
            return "admin/AdacForm";
        }

        return "redirect:/admin/adac";
    }

    @GetMapping("/adac/delete/{adacId}") //등록된 유기견삭제
    public String adacDelete(@PathVariable("adacId") Long adacId, Model model){
        try {
            adacService.deleteAdac(adacId);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "삭제 중 에러가 발생하였습니다.");
            return "admin/AdacForm";
        }

        return "redirect:/admin/adac";
    }

    @GetMapping("/adac/detail/{adacId}") //품종 눌렀을 시 상세페이지
    public String adacDetail(@PathVariable("adacId") Long adacId, Model model){
        try{
            AdacFormDto adacFormDto = adacService.getAdacDtl(adacId);
            model.addAttribute("adacFormDto", adacFormDto);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "상세페이지를 불러오는 중에 에러가 발생하였습니다.");
            return "redirect:/admin/adac";
        }

        return "admin/AdacDtl";
    }
}
