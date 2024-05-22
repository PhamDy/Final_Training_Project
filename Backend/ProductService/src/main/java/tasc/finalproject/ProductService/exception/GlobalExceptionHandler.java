package tasc.finalproject.ProductService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tasc.finalproject.ProductService.model.ErrorResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFoundException(ProductNotFoundException exception,
                                                                           WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
//                                                                  WebRequest webRequest) {
//        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
//                webRequest.getDescription(false),
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                exception.getMessage(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
