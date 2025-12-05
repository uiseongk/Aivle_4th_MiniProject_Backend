package com.example.aivle_4th_MiniProject_team19.Controller;

import com.example.aivle_4th_MiniProject_team19.Service.MemberService;
import com.example.aivle_4th_MiniProject_team19.Service.dto.LoginRequestDto;
import com.example.aivle_4th_MiniProject_team19.Service.dto.LoginResponseDto;
import com.example.aivle_4th_MiniProject_team19.Service.dto.MemberDto;
import com.example.aivle_4th_MiniProject_team19.Controller.ApiResponse;
import com.example.aivle_4th_MiniProject_team19.Service.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ApiResponse<SignupResponseDto> createMember(@RequestBody MemberDto dto) {
        SignupResponseDto response = memberService.createMember(dto);

        return ApiResponse.of(
                "success",
                "가입성공",
                response
        );
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto loginResponse = memberService.login(dto.getUsername(), dto.getPassword());

        return ApiResponse.<LoginResponseDto>builder()
                .status("success")
                .message("로그인 성공")
                .data(loginResponse)
                .build();
    }
}
