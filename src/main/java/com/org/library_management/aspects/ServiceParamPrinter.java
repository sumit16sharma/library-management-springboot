package com.org.library_management.aspects;

import com.org.library_management.dto.request.TxnRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceParamPrinter {

  @Pointcut("execution(* com.org.library_management.service.impl.TxnService.createIssue(com.org.library_management.dto.request.TxnRequest))")
  public void txnServiceCreateMethod() {

  }

  @Before(value = "txnServiceCreateMethod() && args(txnRequest)")
  public void txnServiceAdvice(TxnRequest txnRequest) {
    System.out.println(txnRequest);
  }
}
