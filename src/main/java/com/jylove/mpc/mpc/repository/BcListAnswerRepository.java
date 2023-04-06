package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BcListAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BcListAnswerRepository extends JpaRepository<BcListAnswer, Long> {
    BcListAnswer findByBcListId(Long id);

    void deleteByBcListId(Long id);
}
