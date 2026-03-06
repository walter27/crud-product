package com.produc.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

	PRODUCT_NOT_FOUND("ERR_PRD_001", "Product not found."),
	INVALID_PRODUCT("ERR_PRD_002", "Invalid product parameters."),
	GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred.");

	private final String code;
	private final String message;

	ErrorCatalog(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
