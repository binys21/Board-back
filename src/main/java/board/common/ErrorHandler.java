package board.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){
        ModelAndView mv = new ModelAndView("/error/default");
        mv.addObject("request", request);
        mv.addObject("message", exception.getMessage());
        mv.addObject("stackTrace", exception.getStackTrace());
        return mv;
    }
}
