package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.BestChoiceImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BestChoiceImgRepository extends JpaRepository<BestChoiceImg, Long> {
    List<BestChoiceImg> findByBestChoiceIdOrderByIdAsc(Long id);

    void deleteByBestChoiceId(Long id);
}
