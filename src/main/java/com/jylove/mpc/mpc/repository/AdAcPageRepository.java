package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.AdAcPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AdAcPageRepository extends JpaRepository<AdAcPage, Long>, AdAcPageRepositoryCustom, QuerydslPredicateExecutor<AdAcPage> {
    AdAcPage findByName(String adAcPageName);
}
