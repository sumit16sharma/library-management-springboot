package com.org.library_management.aspects;

import com.org.library_management.controller.BookController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequestResponseLoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);

  // first * is any return type
  // then the package full address
  // then .. is any sub package or sub-class
  // then (**) any function with any no. of arguments

  @Pointcut("execution(* com.org.library_management.controller..*(..))")
  public void controllerMethods() {

  }

  @Before("controllerMethods()")
  // @Before("execute(* org.gfg.JBDL_76_Minor1.controller..*(..))")
  public void logRequest() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    log.info("Request : " +request.getMethod() +" "+ request.getRequestURI());
    log.info("Request Headers");
    request.getHeaderNames().asIterator().forEachRemaining(header-> log.info(header+ ":" + request.getHeader(header)));
  }

  @AfterReturning(value = "controllerMethods()", returning = "response")
  //    @AfterReturning(pointcut = "execute(* org.gfg.JBDL_76_Minor1.controller..*(..))", returning = "response")
  public void logResponse(Object response){
    if(response instanceof HttpServletResponse){
      HttpServletResponse response1 = (HttpServletResponse)  response;
      log.info("respose status" + response1.getStatus());
    }else{
      log.info("Response :" + response.toString());
    }
  }
}
