package com.restapi.template.security.controller;

import com.restapi.template.common.response.ErrorResponse;
import com.restapi.template.security.exception.CantSignInException;
import com.restapi.template.security.exception.IdAlreadyExistsException;
import com.restapi.template.security.exception.InvalidRefreshTokenException;
import com.restapi.template.security.exception.TokenExpiredException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 회원 인증상에서 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {
    /**
     * 아이디 중복 예외 발생
     *
     * @param exception 아이디 중복 예외
     * @return ACCEPTED
     */
    @ExceptionHandler(IdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ErrorResponse handleIdExists(IdAlreadyExistsException exception) {
        return new ErrorResponse(HttpStatus.ACCEPTED, "0001", exception.getMessage());
    }

    /**
     * 회원 인증 예외 발생
     *
     * @param exception 인증 불가 예외
     * @return FORBIDDEN
     */
    @ExceptionHandler(CantSignInException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleUserNotFound(CantSignInException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0002", exception.getMessage());
    }

    /**
     * 잘못된 RefreshToken 예외 발생
     *
     * @param exception 토큰의 유저데이터 불일치
     * @return FORBIDDEN
     */
    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleInvalidRefreshToken(InvalidRefreshTokenException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0003", exception.getMessage());
    }

    /**
     * 토큰 만료 예외 발생
     *
     * @param exception 토큰 만료시간이 지남
     * @return FORBIDDEN
     */
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleTokenExpired(TokenExpiredException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0004", exception.getMessage());
    }

    /**
     * 서명이 유효하지 않은 예외 발생
     *
     * @param exception 서명이 서버와 다름
     * @return FORBIDDEN
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleSignature(SignatureException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0005", "유효하지 않은 토큰입니다.");
    }

    /**
     * 데이터가 깨진 토큰 예외 발생
     *
     * @param exception 토큰을 해석할 수 없음
     * @return FORBIDDEN
     */
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleMalformedJwt(MalformedJwtException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0006", "손상된 토큰입니다.");
    }

    /**
     * Bearer가 아닌 방식으로 인증 예외
     *
     * @param exception 인증 양식을 지키지 않음
     * @return FORBIDDEN
     */
    @ExceptionHandler(DecodingException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleMalformedJwt(DecodingException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0007", "잘못된 인증입니다.");
    }
}
