package sample;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

@Aspect
public class AOPLogging {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(AOPLogging.class.getName());

    @Around("execution(* * (..))")
    public Object intercept(final ProceedingJoinPoint point) throws Throwable {
        final Method method
                = MethodSignature.class.cast(point.getSignature()).getMethod();
        String mName = method.getName();
        String cName = method.getDeclaringClass().getSimpleName();
        LOGGER.log(Level.INFO, "Entering {0}:{1}", new Object[]{cName, mName});
        Object out = null;
        try {
            out = point.proceed();
        } catch (Throwable t) {
            logExceptions(t, point);
        }
        LOGGER.log(Level.INFO, "Exiting {0}:{1}", new Object[]{cName, mName});
        return out;
    }

    private void logExceptions(Throwable t, final ProceedingJoinPoint point) {
        final Method method
                = MethodSignature.class.cast(point.getSignature()).getMethod();
        String mName = method.getName();
        String cName = method.getDeclaringClass().getSimpleName();
        Object[] params = point.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append("Exception caught for [");
        sb.append(cName);
        sb.append(".");
        sb.append(mName);
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];

            sb.append("\n");
            sb.append("  [Arg=").append(i);
            if (param != null) {
                String type = param.getClass().getSimpleName();

                sb.append(", ").append(type);

                // Handle Object Array (Policy Override)
                if (param instanceof Object[]) {
                    sb.append("=").append(Arrays.toString((Object[]) param));
                } else {
                    sb.append("=").append(param.toString());
                }
            } else {
                sb.append(", null");
            }
            sb.append("]");
            sb.append("\n");
        }
        LOGGER.log(Level.SEVERE, sb.toString(), t);

    }
}
