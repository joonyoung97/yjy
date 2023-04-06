package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.dto.NoticeSearchDto;
import com.jylove.mpc.mpc.entity.Notice;
import com.jylove.mpc.mpc.entity.QNotice;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory; //JPA의 동적 쿼리를 사용할때 사용하는 클래스객체

    public NoticeRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchQuery){
        return QNotice.notice.title.like("%" +searchQuery+ "%");
        //searchQuery의 값이 포함된 title 조회
    }

    @Override
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        QueryResults<Notice> results = jpaQueryFactory.selectFrom(QNotice.notice)
                .where(searchByLike(noticeSearchDto.getSearchQuery()))
                .orderBy(QNotice.notice.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Notice> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
