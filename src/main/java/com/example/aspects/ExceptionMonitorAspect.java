package com.example.aspects;


import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;


@Aspect
public class ExceptionMonitorAspect {
    
    @AfterThrowing(pointcut = "execution(* com.example.services..*(..))", throwing = "ex")
    public void logException(Exception ex) {
        System.err.println("Exceção capturada: " + ex.getClass().getName() + " - " + ex.getMessage());
    }

    /*@Around("execution(* com.example.services..*(..))")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Chama o método real
            return joinPoint.proceed();
        } catch (EntityNotFoundException e) {
            // Aqui você pode registrar a exceção ou fazer outro processamento
            System.out.println("Exceção capturada e tratada: " + e.getMessage());

            // Lança uma nova exceção personalizada ou retorne algo diferente
            throw new ResourceNotFoundException("Produto não encontrado"); // Exemplo de manipulação
        }
    }*/

}
