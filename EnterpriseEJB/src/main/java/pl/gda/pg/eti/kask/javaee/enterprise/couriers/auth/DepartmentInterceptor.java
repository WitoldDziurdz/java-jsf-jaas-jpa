package pl.gda.pg.eti.kask.javaee.enterprise.couriers.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Courier;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Department;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;


@Interceptor
@DepartmentAnnotation
@Priority(1000)
public class DepartmentInterceptor extends ElementInterceptor implements Serializable {

    private Department department;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];
        Method method = context.getMethod();
        if (parameter instanceof Department) {
            department = (Department) parameter;
            if (isOperation(method)) {
                return context.proceed();
            }
        }

        throw new EJBAccessException("Client not authorized for this invocation");
    }

    protected boolean isElementOwner() {
        if(department!=null) {
            String login = sessionCtx.getCallerPrincipal().getName();
            Department origin = em.find(Department.class, department.getId());
            em.detach(origin);
            return origin.getOwner().getLogin().equals(login);
        }
        return false;
    }

    protected boolean isNewElement() {
        if(department!=null) {
            return department.getId() == null;
        }
        return false;
    }
}
