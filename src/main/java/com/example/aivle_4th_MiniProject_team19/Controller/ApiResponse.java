package com.example.aivle_4th_MiniProject_team19.Controller;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@Getter
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /** HttpStatus 기반 (필요하면 사용) */
    public static <T> ApiResponse<T> of(org.springframework.http.HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus.name().toLowerCase(), httpStatus.name(), data);
    }

    /** 문자열 기반 (회원가입/로그인 커스텀 메시지용) */
    public static <T> ApiResponse<T> of(String status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    /** 기본 성공 응답 */
    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>("success", "OK", data);
    }
}
