package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BCmatching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BCmatchingRepository  extends JpaRepository<BCmatching, Long> {
    List<BCmatching> findByMemberId(Long memid);

    BCmatching findByBestChoiceId(Long bcid);
}
