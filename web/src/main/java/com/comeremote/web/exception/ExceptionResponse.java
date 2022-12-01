package com.comeremote.web.exception;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ExceptionResponse {

	private int statusCode;
	private HttpStatus status;
	private List<String> messages;

}
