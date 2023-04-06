package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.constant.Inmate;
import com.jylove.mpc.mpc.constant.ParentingExperience;
import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.MemberFormDto;
import com.jylove.mpc.mpc.dto.NoticeFormDto;
import com.jylove.mpc.mpc.entity.Member;
import com.jylove.mpc.mpc.service.AdMemberManageService;
import com.jylove.mpc.mpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdMemberManageController {
    private final AdMemberManageService adMemberManageService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = {"/management" , "/management/{page}"}) // 회원정보 페이지
    public String memberManage(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Member> members = adMemberManageService.getMemberMangePage(pageable);

        List<MemberFormDto> companionlist = memberService.memberFormDtos(members);

        model.addAttribute("companionlist", companionlist);
        model.addAttribute("memberTotalCount", members.getTotalElements());
        model.addAttribute("members", members);
        model.addAttribute("maxPage", 5);
        return "admin/AD_MemberManagement";
    }


    @GetMapping("/management/update/{memberId}") // 수정 버튼 눌렀을 시 상세페이지
    public String managementUpdate(@PathVariable("memberId") Long memberId, Model model){
        try {
            MemberFormDto memberFormDto = adMemberManageService.getMemberMangeDetail(memberId);
            model.addAttribute("memberFormDto", memberFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 회원이 없습니다.");
            model.addAttribute("memberFormDto",new MemberFormDto());
            return "admin/AD_UpdateMemberManagement";
        }
        return "admin/AD_UpdateMemberManagement";
    }

    @PostMapping(value = "/management/update/{memberId}") // 회원수정
    public String managementUpdate(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        System.out.println("asasasas");
//        if (bindingResult.hasErrors()) {
//            System.out.println("hasError");
//            return "admin/AD_UpdateMemberManagement";
//        }
        try {
            System.out.println("nmnmnmnm");
            adMemberManageService.updateMemberMange(memberFormDto,passwordEncoder);
        }catch (Exception e){
            model.addAttribute("errorMessage", "회원 수정 중 에러가 발생하였습니다.");
            return "admin/AD_UpdateMemberManagement";
        }
        return "redirect:/management";
    }

    @GetMapping("/management/delete/{memberId}")
    public String deleteMemberManage(@PathVariable("memberId") Long memberId, Model model) {
        try {
            adMemberManageService.deleteMemberManage(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "회원 삭제 중 에러가 발생하였습니다.");
            return "admin/AD_MemberManagement";
        }
        return "redirect:/management";
    }

    @ModelAttribute("inmates") // 동거인 유,무 라디오 선택
    public Inmate[] inmates() {
        return Inmate.values();
    }
    @ModelAttribute("parentingExperiences") // 양육경험 콤보박스 선택
    public ParentingExperience[] parentingExperiences() {
        return ParentingExperience.values();
    }
    @ModelAttribute("companionChoice") // 관심동물 체크박스 선택(중복가능)
    public Map<String, String> companionChoice() {
        Map<String , String> companionChoice = new LinkedHashMap<>();
        companionChoice.put("dog","강아지");
        companionChoice.put("cat","고양이");
        return companionChoice;
    }


}
