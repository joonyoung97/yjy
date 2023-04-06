package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.AdacSearchDto;
import com.jylove.mpc.mpc.entity.BCmatching;
import com.jylove.mpc.mpc.entity.BestChoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BestChoiceRepositoryCustom {
    public Page<BestChoice> getAdminAdacPage(AdacSearchDto adacSearchDto, Pageable pageable);

    public Page<BCmatching> getBcMatchingPage(Long id, Pageable pageable);
}
