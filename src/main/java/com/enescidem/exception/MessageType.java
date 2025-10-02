package com.enescidem.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	NO_RECORD_EXIST("1001","Kayıt bulunamadı"),
	GENERAL_EXCEPTION("999","Genel bir hata oluştu"),
	TOKEN_EXPIRED("1002", "JWT süresi doldu"),
	INVALID_TOKEN("1003", "JWT geçersiz");


    private final String code;
    private final String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
