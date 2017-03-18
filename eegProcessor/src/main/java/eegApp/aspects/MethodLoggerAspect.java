package eegApp.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MethodLoggerAspect {

	private static final Logger LOGGER = Logger
			.getLogger(MethodLoggerAspect.class);

	@Pointcut("execution(* service.*.*(..))")
	private void selectAllServiceMethods() {
	}

	@Before("selectAllServiceMethods()")
	public void beforeAdvice(JoinPoint point) {
		LOGGER.debug("Going to call method " + point.getSignature().getName()
				+ " on class " + point.getSignature().getDeclaringTypeName());
	}

}
