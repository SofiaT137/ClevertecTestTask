package by.clevertec.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * The "AspectLogging" class presents the logic of logging aspects of the application
 */
@Component
@Aspect
@Slf4j
public class AspectLogging {

    private static final String EXECUTION_ALL_SERVICE_METHODS = "execution(* by.clevertec.service.*.*(..))";
    private static final String EXECUTION_ALL_CONTROLLERS =
            "within(@org.springframework.web.bind.annotation.RestController *)";
    private static final String SERVICE_POINTCUT_REFERENCE_NAME = "allServiceMethods()";
    private static final String SERVICE_LAYER_METHOD_THROWS_AN_EXCEPTION =
            "Method from Service layer throws an exception: ";
    private static final String EXCEPTION = "exception";
    private static final String DOUBLE_BRACKETS = "{} {}";
    private static final String METHOD_NAME = "Method name: ";
    private static final String GETTING_RESPONSE = "Getting response : ";
    private static final String METHOD_PARAMETERS = " METHOD PARAMETERS: ";

    /**
     * The method is used to create "Service" layer pointcut
     */
    @Pointcut(EXECUTION_ALL_SERVICE_METHODS)
    private void allServiceMethods() {}


    /**
     * After throwing exceptions from all the service layer methods advice method
     * @param exception Throwable exception
     */
    @AfterThrowing(pointcut = SERVICE_POINTCUT_REFERENCE_NAME, throwing = EXCEPTION)
    public void afterThrowingServiceMethodsAdvice(Throwable exception) {
        log.error(DOUBLE_BRACKETS, SERVICE_LAYER_METHOD_THROWS_AN_EXCEPTION, exception.getMessage());
    }

    /**
     * Around all the app controllers advice method
     * @param proceedJoinPoint ProceedingJoinPoint proceedJoinPoint
     */
    @Around(EXECUTION_ALL_CONTROLLERS)
    public Object aroundAllControllersAdvice(ProceedingJoinPoint proceedJoinPoint) throws Throwable {
        log.info(METHOD_NAME + proceedJoinPoint.getSignature().getName() + METHOD_PARAMETERS
                + Arrays.toString(proceedJoinPoint.getArgs()));
        Object answer = proceedJoinPoint.proceed();
        log.info(METHOD_NAME + proceedJoinPoint.getSignature().getName() + GETTING_RESPONSE +
                answer);
        return answer;
    }
}