package pl.gda.pg.eti.kask.javaee.enterprise.couriers.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Element;
import pl.gda.pg.eti.kask.javaee.enterprise.users.PermissionService;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;


@Interceptor
@RoleAnnotation
@Priority(1000)
public class RoleInterceptor implements Serializable {
    @Inject
    PermissionService permissionService;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];
        Method method = context.getMethod();
        if (parameter instanceof Element) {
            Element element = (Element) parameter;

            if (permissionService.isOperation(method.getName(), element)) {
                return context.proceed();
            }
        }
        throw new EJBAccessException("Client not authorized for this invocation");
    }
}
