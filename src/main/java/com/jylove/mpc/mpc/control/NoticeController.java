package com.jylove.mpc.mpc.control;

import com.jylove.mpc.mpc.dto.NoticeFormDto;
import com.jylove.mpc.mpc.dto.NoticeSearchDto;
import com.jylove.mpc.mpc.entity.Notice;
import com.jylove.mpc.mpc.service.NoticeService;
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
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping(value = {"/notice", "/notice/{page}"}) //공지사항 전체 뷰페이지, 페이지 번호에 따른 뷰페이지
    public String pNotice(NoticeSearchDto noticeSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        //페이징을 위한 부분 현재 등록된 공지사항이 총 10개라면 페이징 수는 4페이지가 된다. 1페이지에 공지사항 3개씩 보여주게 설정했다.
        //3 이라는 숫자가 들어있는 부분이 한페이지에 표시할 공지사항 갯수이다.
        Page<Notice> notices = noticeService.getAdminNoticePage(noticeSearchDto, pageable);
        //해당 페이지에 표시할 내용들과 페이징 정보

        model.addAttribute("noticeTotalCount", notices.getTotalElements()); //공지사항 순번을 위한 데이터 갯수
        model.addAttribute("notices", notices);
        model.addAttribute("maxPage", 5);

        return "admin/Notice";
    }

    @GetMapping("/notice/new")
    public String pNoticeForm(Model model){
        model.addAttribute("noticeFormDto", new NoticeFormDto());

        return "admin/NoticeForm";
    }

    @PostMapping("/notice/new") //공지사항 등록
    public String pNoticeNew(@Valid NoticeFormDto noticeFormDto, BindingResult br, Model model,
                             @RequestParam("noticeImgFile")MultipartFile noticeImgFile){
        if(br.hasErrors()){ //공지사항 등록시 필수 입력값이 없을 시
            return "admin/NoticeForm";
        }

        try{
            noticeService.saveNotice(noticeFormDto, noticeImgFile); //공지사항 등록 실행
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("공지사항 등록 실패");
            model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생하였습니다.");

            return "admin/NoticeForm";
        }

        return "redirect:/admin/notice";
    }

    @GetMapping("/notice/update/{noticeId}") //공지사항리스트에서 수정 버튼 눌렀을 시 상세페이지
    public String pNoticeUpdate(@PathVariable("noticeId") Long noticeId, Model model){
        try {
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeId);
            model.addAttribute("noticeFormDto", noticeFormDto);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "등록된 공지사항이 없습니다.");
        }

        return "admin/NoticeForm";
    }

    @PostMapping("/notice/update/{noticeId}") //공지사항 상세페이지에서 수정 실행
    public String pNoticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult br,
                                @RequestParam("noticeImgFile") MultipartFile multipartFile, Model model){
        if(br.hasErrors()){
            return "admin/NoticeForm";
        }

        try{
            noticeService.updateNotice(noticeFormDto, multipartFile); //공지사항 수정 실행
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("공지사항 수정 실패");
            model.addAttribute("errorMessage", "공지사항 수정 중 에러가 발생하였습니다.");
            return "admin/NoticeForm";
        }

        return "redirect:/admin/notice";
    }

    @GetMapping("/notice/delete/{noticeId}")
    public String pNoticeEnroll(@PathVariable("noticeId") Long noticeId, Model model){
        try {
            noticeService.deleteNotice(noticeId);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("공지사항 삭제 실패");
            model.addAttribute("errorMessage", "공지사항 삭제 중 에러가 발생하였습니다.");
            return "admin/NoticeForm";
        }

        return "redirect:/admin/notice";
    }
    @GetMapping("/notice/detail/{noticeId}") //제목 누를 시 상세페이지
    public String pNoticeDetail(@PathVariable Long noticeId, Model model){
        try {
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeId);
            model.addAttribute("noticeFormDto", noticeFormDto);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage", "상세페이지를 불러오는 중에 에러가 발생하였습니다.");
            return "redirect:/admin/notice";
        }

        return "admin/NoticeDtl";
    }
}
