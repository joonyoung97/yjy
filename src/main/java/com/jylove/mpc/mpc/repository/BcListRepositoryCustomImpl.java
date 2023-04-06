package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BcList;
import com.jylove.mpc.mpc.entity.QBcList;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class BcListRepositoryCustomImpl implements BcListRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;
    // JPA의 동적 쿼리를 사용할 때 사용하는 클래스 객체

    public BcListRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BcList> getBcListPage(Pageable pageable) {
        QueryResults<BcList> results = jpaQueryFactory
                .selectFrom(QBcList.bcList)
                .orderBy(QBcList.bcList.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BcList> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BcList> getUserBcListPage(String email, Pageable pageable) {
        QueryResults<BcList> results = jpaQueryFactory
                .selectFrom(QBcList.bcList)
                .where(QBcList.bcList.email.eq(email))
                .orderBy(QBcList.bcList.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BcList> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


}