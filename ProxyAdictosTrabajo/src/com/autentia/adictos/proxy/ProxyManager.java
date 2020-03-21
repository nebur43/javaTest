package com.autentia.adictos.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyManager implements InvocationHandler {
    
    IUserManager realManager = null;
    
    public ProxyManager(IUserManager realManager) {
        this.realManager = realManager;
    }
    
    /* Este es el m�todo callback que ser� invocado previamente a cada m�todo de          los managers. */
    public Object invoke(Object proxy, Method method, Object[] args) 
        throws Throwable {        
        
        System.out.println("* PROYX * => A Manager is being invoked:"+method.getName());
        
        // Continuamos invocando al Manager real.
        return method.invoke(realManager,args);
    }

}