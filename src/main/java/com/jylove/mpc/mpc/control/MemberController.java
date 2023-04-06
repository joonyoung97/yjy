package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.constant.Inmate;
import com.jylove.mpc.mpc.constant.ParentingExperience;
import com.jylove.mpc.mpc.dto.MemberFormDto;
import com.jylove.mpc.mpc.entity.Member;
import com.jylove.mpc.mpc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.LinkedHashMap;

import java.util.Map;

@RequestMapping(value = "/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "Member/MemberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult br, Model model ){
        if(br.hasErrors()){
            return "Member/MemberForm";
        }
        try{
            Member mb = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(mb, memberFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "Member/MemberForm";
        }
        return "redirect:/";
    }
    @GetMapping(value = "/login") // 로그인성공 시
    public String loginMember() {
        return "Member/MemberLoginForm";
    }

    @GetMapping(value = "/login/error") // 로그인실패 시
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "Member/MemberLoginForm";
    }

    @ModelAttribute("inmates") // 동거인 유,무 라디오 선택
    public Inmate[] inmates() {
        return Inmate.values(); // enum의 모든정보를 배열로담아준다.
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

    @GetMapping(value = "/updateForm") // 내 정보
    public String updateForm(Principal principal, Model model) {
        String email = principal.getName();
        MemberFormDto memberFormDto = memberService.getUserDtl(email);
        model.addAttribute("memberFormDto",memberFormDto);
        return "Member/MypageForm";
    }
    @GetMapping(value = "/Mypage")
    public String memberUpdate() {
        return "Member/Mine";
    }
    @PostMapping(value = "/updateForm/{memberId}") // 유저 정보 수정
    public String memberUpdate(@Valid MemberFormDto memberFormDto, BindingResult br, Model model ){
        try{
            memberService.memberUpdate(memberFormDto,passwordEncoder);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","정보수정에러");
            return "Member/MypageForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/userDelete/{id}") // 회원 탈퇴
    public String memberDelete(@PathVariable("id") Long id) {
        memberService.memberDelete(id);
        return "redirect:/members/logout";
    }

    @GetMapping("/findIdPw") // 아이디 비밀번호 찾기 페이지
    public String findId(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "Member/FindIdAndPw";
    }
    @PostMapping("/findId") // 아이디 찾기 정보
    public String findId(@RequestParam String name, @RequestParam String tel, Model model){
        try{
            String email = memberService.findId(name,tel);
            model.addAttribute("email",email);
            model.addAttribute("name",name);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","아이디찾기실패");
            return "Member/FindIdAndPw";
        }
        return "Member/FindMyId";
    }
    @PostMapping("/findPw") // 메일 전송
    public String findPw(@RequestParam String email, Model model) {
        memberService.sendEmailAndPw(email, passwordEncoder);
        return "Member/FindMyPassword";
    }
}

