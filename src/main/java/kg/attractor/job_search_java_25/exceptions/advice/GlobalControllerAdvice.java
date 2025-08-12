package kg.attractor.job_search_java_25.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import kg.attractor.job_search_java_25.exceptions.types.BadRequestException;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleEmptyResultDataAccessException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(ConflictException.class)
    public String handleConflictException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleMessageNotReadable(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleServletRequestParameter(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public String handleMissingServletRequestPart(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.PAYLOAD_TOO_LARGE.value());
        model.addAttribute("reason", HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public String handleHttpServerErrorException(HttpServletRequest request, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler({
            org.springframework.context.ApplicationContextException.class,
            freemarker.core.ParseException.class
    })
    public String handleExFreemarker(HttpServletRequest request, Model model, Exception ex) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", "Internal Server Error");
        model.addAttribute("details", request);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAny(HttpServletRequest req, Model model, Exception ex) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", "Internal Server Error");
        model.addAttribute("details", req);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(freemarker.template.TemplateException.class)
    public String handleTemplate(HttpServletRequest req, Model model, Exception ex) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", "Template Error");
        model.addAttribute("details", req);
        model.addAttribute("message", ex.getMessage());
        return "errors/error";
    }


}