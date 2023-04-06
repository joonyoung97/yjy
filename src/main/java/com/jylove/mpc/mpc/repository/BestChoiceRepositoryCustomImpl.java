package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.AdacSearchDto;
import com.jylove.mpc.mpc.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class BestChoiceRepositoryCustomImpl implements BestChoiceRepositoryCustom{
    private JPAQueryFactory jpaQueryFactory; //JPA의 동적 쿼리를 사용할때 사용하는 클래스객체

    public BestChoiceRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

//    private BooleanExpression adacSearchByLike(String searchQuery){
//        return QBestChoice.bestChoice..like("%" +searchQuery+ "%");
//        //searchQuery의 값이 포함된 title 조회
//    }

    private BooleanExpression searchAdacPageEq(String searchAdacPage){
        return searchAdacPage == null ? null : QAdAcPage.adAcPage.name.eq(searchAdacPage);
    }

    private BooleanExpression searchAnimalEq(String searchAnimal){
        return searchAnimal == null ? null : QBestChoice.bestChoice.animal.eq(searchAnimal);
    }

    private BooleanExpression searchCultivarEq(String searchCultivar){
        return searchCultivar == null ? null : QBestChoice.bestChoice.cultivar.eq(searchCultivar);
    }

    @Override
    public Page<BestChoice> getAdminAdacPage(AdacSearchDto adacSearchDto, Pageable pageable) {
        QueryResults<BestChoice> results = jpaQueryFactory.selectFrom(QBestChoice.bestChoice)
                .where(searchAdacPageEq(adacSearchDto.getSearchAdacPage()),
                        searchAnimalEq(adacSearchDto.getSearchAnimal()),
                        searchCultivarEq(adacSearchDto.getSearchCultivar()))
                .orderBy(QBestChoice.bestChoice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BestChoice> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<BCmatching> getBcMatchingPage(Long id, Pageable pageable) {
        QueryResults<BCmatching> results = jpaQueryFactory.selectFrom(QBCmatching.bCmatching)
                .where(QBCmatching.bCmatching.memberId.eq(id))
                .orderBy(QBCmatching.bCmatching.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BCmatching> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
