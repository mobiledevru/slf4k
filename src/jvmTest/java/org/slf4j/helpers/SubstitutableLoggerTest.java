/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.event.EventRecodingLogger;

/**
 * @author Chetan Mehrotra
 */
public class SubstitutableLoggerTest {
    private static final Set<String> EXCLUDED_METHODS = new HashSet<String>(Arrays.asList("getName"));

    @Test
    public void testDelegate() throws Exception {
        SubstituteLogger log = new SubstituteLogger("foo", null, false);
        assertTrue(log.delegate() instanceof EventRecodingLogger);

        Set<String> expectedMethodSignatures = determineMethodSignatures(Logger.class);
        LoggerInvocationHandler ih = new LoggerInvocationHandler();
        Logger proxyLogger = (Logger) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { Logger.class }, ih);
        log.setDelegate(proxyLogger);

        invokeMethods(log);

        // Assert that all methods are delegated
        expectedMethodSignatures.removeAll(ih.getInvokedMethodSignatures());
        if (!expectedMethodSignatures.isEmpty()) {
            fail("Following methods are not delegated " + expectedMethodSignatures.toString());
        }
    }

    private void invokeMethods(Logger proxyLogger) throws InvocationTargetException, IllegalAccessException {
        for (Method m : Logger.class.getDeclaredMethods()) {
            if (!EXCLUDED_METHODS.contains(m.getName())) {
                final Class<?>[] types = m.getParameterTypes();
                final Object[] arg = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    final Class<?> type = types[i];
                    if (type.isArray()) {
                        arg[i] = new Object[0]; // vararg is unable to accept null
                    } else {
                        arg[i] = null;
                    }
                }
                m.invoke(proxyLogger, arg);
            }
        }
    }

    private class LoggerInvocationHandler implements InvocationHandler {
        private final Set<String> invokedMethodSignatures = new HashSet<String>();

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            invokedMethodSignatures.add(getMethodSignature(method));
            if (method.getName().startsWith("is")) {
                return true;
            }
            if (method.getName().equals("equals")) {
                return false;
            }
            if (method.getName().equals("hashCode")) {
                return 0;
            }
            if (method.getName().equals("toString")) {
                return "proxy";
            }
            return null;
        }

        public Set<String> getInvokedMethodSignatures() {
            return invokedMethodSignatures;
        }
    }

    private static Set<String> determineMethodSignatures(Class<Logger> loggerClass) {
        Set<String> methodSignatures = new HashSet<String>();
        for (Method m : loggerClass.getDeclaredMethods()) {
            if (!EXCLUDED_METHODS.contains(m.getName())) {
                methodSignatures.add(getMethodSignature(m));
            }
        }
        return methodSignatures;
    }

    private static String getMethodSignature(Method m) {
        List<String> result = new ArrayList<String>();
        result.add(m.getName());
        for (Class<?> clazz : m.getParameterTypes()) {
            result.add(clazz.getSimpleName());
        }
        return result.toString();
    }
}
