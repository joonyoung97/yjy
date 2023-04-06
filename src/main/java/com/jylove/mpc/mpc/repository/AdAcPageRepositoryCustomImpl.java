package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.AdAcPageSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.QAdAcPage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class AdAcPageRepositoryCustomImpl implements AdAcPageRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public AdAcPageRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }



    private BooleanExpression searchByLike(String searchQuery) {

        return QAdAcPage.adAcPage.name.like("%"+searchQuery+"%");

        // searchBy의 값에 따라 상품 또는 상품 등록자의 아이디에 검색어가 포함되어 있는 상품을 조회
    }


    @Override
    public Page<AdAcPage> getItem(Pageable pageable, AdAcPageSearchDto adAcPageSearchDto) {

        QueryResults<AdAcPage> results = queryFactory
                .selectFrom(QAdAcPage.adAcPage)
                .where(searchByLike(
                        adAcPageSearchDto.getSearchQuery()))

                .orderBy(QAdAcPage.adAcPage.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<AdAcPage> content = results.getResults();
        Long total = results.getTotal();


        return new PageImpl<>(content, pageable, total);
    }
}
