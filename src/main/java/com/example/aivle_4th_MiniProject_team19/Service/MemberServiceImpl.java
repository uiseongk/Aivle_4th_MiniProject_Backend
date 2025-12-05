package com.example.aivle_4th_MiniProject_team19.Service;

import com.example.aivle_4th_MiniProject_team19.Entity.Member;
import com.example.aivle_4th_MiniProject_team19.Repository.MemberRepository;
import com.example.aivle_4th_MiniProject_team19.Service.dto.LoginResponseDto;
import com.example.aivle_4th_MiniProject_team19.Service.dto.MemberDto;
import com.example.aivle_4th_MiniProject_team19.Service.dto.SignupResponseDto;
import com.example.aivle_4th_MiniProject_team19.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public SignupResponseDto createMember(MemberDto dto) {

        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        Member saved = memberRepository.save(member);

        return SignupResponseDto.builder()
                .userId(saved.getId())
                .name(saved.getUsername())
                .build();
    }

    @Override
    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return MemberDto.from(member);
    }

    @Override
    public LoginResponseDto login(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!member.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        return LoginResponseDto.builder()
                .userId(member.getId().toString()) // spec은 UUID지만, 지금은 id를 문자열로
                .username(member.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
