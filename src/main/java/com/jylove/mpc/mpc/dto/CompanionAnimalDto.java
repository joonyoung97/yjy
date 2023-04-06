package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.CompanionAnimal;
import com.jylove.mpc.mpc.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CompanionAnimalDto {
    private Long id;

    private Member member;
    private String companionChoice;

    private static ModelMapper modelMapper = new ModelMapper();
    public static CompanionAnimalDto of(CompanionAnimal companionAnimal){
        return modelMapper.map(companionAnimal, CompanionAnimalDto.class);
    }
}
