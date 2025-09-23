package kg.attractor.job_search_java_25.exceptions.advice;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.job_search_java_25.exceptions.types.*;
import kg.attractor.job_search_java_25.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.NullPointerException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final ErrorService errorService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex,
                                   Model model,
                                   HttpServletResponse response) {

        var body = errorService.fromBindingResult(ex.getBindingResult());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("errors", body.getResponse());
        model.addAttribute("title", body.getTitle());

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        return "errors/error";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("stackTrace", ex.getStackTrace());
        model.addAttribute("cause", ex.getCause());
        return "errors/error";
    }

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

    @ExceptionHandler(NotFoundException.class)
    public String notFound(HttpServletRequest request, Model model, NotFoundException ex) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", ex.getMessage());

        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAny(HttpServletRequest req, Model model, Exception ex) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", "Ошибка какая-то");
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

//    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
//    public String handleMissingServlet(HttpServletRequest req, Model model, MissingServletRequestParameterException ex) {
//
//        model.addAttribute("status", 400);
//        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
//        model.addAttribute("details", req);
//        model.addAttribute("message", ex.getMessage());
//        return "errors/error";
//    }

}