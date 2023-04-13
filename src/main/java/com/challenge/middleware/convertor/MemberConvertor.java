package com.challenge.middleware.convertor;

import com.challenge.middleware.dto.MemberDTO;
import com.challenge.middleware.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConvertor {

    public MemberDTO convertToDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO(member.getMemberId(), member.getName());
        return memberDTO;
    }

    public Member convertToEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setMemberId(memberDTO.getIdMember());
        member.setName(memberDTO.getName());
        return member;
    }

}
