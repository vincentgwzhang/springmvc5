package org.personal.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason = "Student could not found")
public class StudentNotFoundException extends RuntimeException
{

}
