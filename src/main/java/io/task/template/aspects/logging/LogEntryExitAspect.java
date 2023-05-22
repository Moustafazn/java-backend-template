package io.task.template.aspects.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;


@Aspect
@Component
public class LogEntryExitAspect {

    @Value("${Log.entry.aspect.show-args}")
    private boolean showArgs;

    String entry(final String methodName, final String[] params, final Object[] args, final String statusInfo) {
        final StringJoiner message = new StringJoiner(" ")
                .add(statusInfo).add(methodName).add("method");
        if (this.showArgs && Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {
            final Map<String, Object> values = new HashMap<>(params.length);
            for (int i = 0; i < params.length; i++) values.put(params[i], args[i]);
            message.add("with args:")
                    .add(values.toString());
        }
        return message.toString();
    }

    String exit(final String methodName, final String duration, final Object result, final boolean showResult, final boolean showExecutionTime) {
        final StringJoiner message = new StringJoiner(" ")
                .add("Finished").add(methodName).add("method");
        if (showExecutionTime) message.add("in").add(duration);
        if (showResult) message.add("with return:").add(result.toString());
        return message.toString();
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(com.task.points..*)" +
            " || within(io.task.template.services..*)" +
            " || within(io.task.template.api..*)")
    public void applicationPackagePointcut() {
    }

    /*
    * if you want to use this method please add to the specific method in service this line:
    *   @LogEntryExit(showArgs = true, showResult = true, unit = ChronoUnit.SECONDS)
     */
    @Around("@annotation(io.task.template.aspects.logging.LogEntryExit)")
    public Object logAround(final ProceedingJoinPoint point) throws Throwable {
        final var codeSignature = (CodeSignature) point.getSignature();
        final var methodSignature = (MethodSignature) point.getSignature();
        final Method method = methodSignature.getMethod();
        final Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        final var annotation = method.getAnnotation(LogEntryExit.class);
        final ChronoUnit unit = annotation.unit();
        final boolean showResult = annotation.showResult();
        final boolean showExecutionTime = annotation.showExecutionTime();
        final String methodName = method.getName();
        final Object[] methodArgs = point.getArgs();
        final String[] methodParams = codeSignature.getParameterNames();
        logger.info(entry(methodName, methodParams, methodArgs, "Started"));
        final var start = Instant.now();
        final var response = point.proceed();
        final var end = Instant.now();
        final var duration = String.format("%s %s", unit.between(start, end), unit.name().toLowerCase());
        logger.info(exit(methodName, duration, response, showResult, showExecutionTime));
        return point.proceed();
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "throwable")
    public void logAfterThrowing(final JoinPoint point, final Throwable throwable) throws Throwable {
        final var codeSignature = (CodeSignature) point.getSignature();
        final var methodSignature = (MethodSignature) point.getSignature();
        final Method method = methodSignature.getMethod();
        final Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        final String methodName = method.getName();
        final Object[] methodArgs = point.getArgs();
        final String[] methodParams = codeSignature.getParameterNames();
        logger.error(entry(methodName, methodParams, methodArgs, "Exception from"), throwable);
    }
}

