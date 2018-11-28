package pl.gda.pg.eti.kask.javaee.enterprise.couriers.auth;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Pack;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;


@Interceptor
@PackAnnotation
@Priority(1000)
public class PackInterceptor extends ElementInterceptor implements Serializable {

    private Pack pack;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];
        Method method = context.getMethod();
        if (parameter instanceof Pack) {
            pack = (Pack) parameter;

            if (isOperation(method)) {
                return context.proceed();
            }
        }

        throw new EJBAccessException("Client not authorized for this invocation");
    }


    protected boolean isNewOrOwnerElement(){
        if(pack!=null) {
            if(isNewPack(pack)||isPackOwner(pack)){
                return true;
            }
        }
        return false;
    }

    private boolean isPackOwner(Pack pack) {
        String login = sessionCtx.getCallerPrincipal().getName();
        Pack originalPack = em.find(Pack.class, pack.getId());
        em.detach(originalPack);
        return originalPack.getOwner().getLogin().equals(login);
    }

    private boolean isNewPack(Pack pack) {
        return pack.getId() == null;
    }
}
