package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
    Member findByEmail(String email);
    Member findByNameAndTel(String name, String tel);
}
