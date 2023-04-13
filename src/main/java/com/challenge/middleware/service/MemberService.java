package com.challenge.middleware.service;

import com.challenge.middleware.convertor.MemberConvertor;
import com.challenge.middleware.dto.MemberDTO;
import com.challenge.middleware.entity.Member;
import com.challenge.middleware.entity.PokerPlanningSession;
import com.challenge.middleware.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private PokerPlanningSessionService sessionService;
    private MemberRepository memberRepository;
    private MemberConvertor memberConvertor;

    public MemberService(MemberRepository memberRepository, MemberConvertor memberConvertor, PokerPlanningSessionService sessionService) {
        this.memberRepository = memberRepository;
        this.memberConvertor = memberConvertor;
        this.sessionService = sessionService;
    }

    public List<MemberDTO> getSessionMembers(String idSession) {
        Optional<List<Member>> optMembers = Optional.ofNullable(memberRepository.findAllBySessionId(idSession));
        if (optMembers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        return optMembers.get().stream().map(s -> memberConvertor.convertToDTO(s)).toList();
    }

    @Transactional
    public List<MemberDTO> logoutMember(String idSession, String idMember) {
        Optional<Member> optMember = Optional.ofNullable(memberRepository.findBySessionIdAndMemberId(idSession, idMember));
        PokerPlanningSession session = sessionService.findSessionEntity(idSession);
        List<Member> sessionMembers = session.getMembers();
        if (optMember.isPresent() && !sessionMembers.isEmpty()) {
            Member member = optMember.get();
            sessionMembers.remove(member);

            member.setPokerPlanningSession(null);
            memberRepository.saveAndFlush(member);

            return sessionMembers.stream().map(m -> memberConvertor.convertToDTO(m)).toList();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public MemberDTO joinSession(String idSession, MemberDTO memberDTO) {
        Optional<Member> optMember = Optional.ofNullable(memberRepository.findByMemberId(memberDTO.getIdMember()));
        if (optMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = optMember.get();
        PokerPlanningSession session = sessionService.findSessionEntity(idSession);

        member.setName(memberDTO.getName());
        member.setPokerPlanningSession(session);

        memberRepository.save(member);

        return memberConvertor.convertToDTO(member);
    }

    protected Member findMember(String idSession, String idMember) {
        Optional<Member> optMember = Optional.ofNullable(memberRepository.findBySessionIdAndMemberId(idSession, idMember));
        if (optMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        return optMember.get();
    }
}
