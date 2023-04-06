package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BcList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BcListRepositoryCustom {
    public Page<BcList> getBcListPage(Pageable pageable);

    public Page<BcList> getUserBcListPage(String email, Pageable pageable);
}
