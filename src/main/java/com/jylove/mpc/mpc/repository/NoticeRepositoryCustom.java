package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.NoticeSearchDto;
import com.jylove.mpc.mpc.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);
}
