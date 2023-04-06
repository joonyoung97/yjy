package com.jylove.mpc.mpc.repository;

import com.jylove.mpc.mpc.entity.CompanionAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanionAnimalRepository extends JpaRepository<CompanionAnimal, Long> {
    void deleteByMemberId(Long id);
    List<CompanionAnimal> findByMemberIdOrderByIdAsc(Long id);

    CompanionAnimal findByMemberId(Long id);

}
