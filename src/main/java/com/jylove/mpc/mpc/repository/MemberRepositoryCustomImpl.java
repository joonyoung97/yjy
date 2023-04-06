package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.constant.Role;
import com.jylove.mpc.mpc.entity.Member;
import com.jylove.mpc.mpc.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private JPAQueryFactory jpaQueryFactory;
    public MemberRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Member> getMemberMangePage(Pageable pageable) {
        QueryResults<Member> results = jpaQueryFactory.selectFrom(QMember.member)
                .where(QMember.member.role.eq(Role.USER))
                .orderBy(QMember.member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Member> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
