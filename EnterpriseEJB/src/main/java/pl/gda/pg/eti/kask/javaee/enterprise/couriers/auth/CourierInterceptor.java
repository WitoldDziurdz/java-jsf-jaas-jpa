package pl.gda.pg.eti.kask.javaee.enterprise.couriers.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;


@Interceptor
@CourierAnnotation
@Priority(1000)
public class CourierInterceptor extends ElementInterceptor implements Serializable {

    private Courier courier;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];
        Method method = context.getMethod();
        if (parameter instanceof Courier) {
            courier = (Courier) parameter;

            if (isOperation(method)) {
                return context.proceed();
            }
        }

        throw new EJBAccessException("Client not authorized for this invocation");
    }

    protected boolean isElementOwner() {
        if(courier!=null) {
            String login = sessionCtx.getCallerPrincipal().getName();
            Courier origin = em.find(Courier.class, courier.getId());
            em.detach(origin);
            return origin.getOwner().getLogin().equals(login);
        }
        return false;
    }

    protected boolean isNewElement() {
        if(courier!=null) {
            return courier.getId() == null;
        }
        return false;
    }
}
