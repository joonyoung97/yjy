package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.AdAcPageSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdAcPageRepositoryCustom {
    Page<AdAcPage> getItem(Pageable pageable, AdAcPageSearchDto adAcPageSearchDto);  //추상메서드
}
