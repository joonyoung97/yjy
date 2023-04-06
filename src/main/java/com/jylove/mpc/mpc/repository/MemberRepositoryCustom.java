package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<Member> getMemberMangePage(Pageable pageable);
}
