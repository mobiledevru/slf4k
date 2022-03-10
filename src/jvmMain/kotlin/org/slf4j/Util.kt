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
package org.slf4j

/**
 * An internal utility class.
 *
 * @author Alexander Dorokhine
 * @author Ceki G&uuml;lc&uuml;
 */
actual object Util {

    fun safeGetSystemProperty(key: String): String? {
        requireNotNull(key) { "null input" }
        var result: String? = null
        try {
            result = java.lang.System.getProperty(key)
        } catch (sm: SecurityException) {
            // ignore
        }
        return result
    }

    fun safeGetBooleanSystemProperty(key: String): Boolean {
        val value = safeGetSystemProperty(key)
        return value?.equals("true", ignoreCase = true) ?: false
    }

    private var SECURITY_MANAGER: ClassContextSecurityManager? = null
    private var SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false
    private fun getSecurityManager() : ClassContextSecurityManager? {
        return if (SECURITY_MANAGER != null) SECURITY_MANAGER else
            if (SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED) null else {
                SECURITY_MANAGER = safeCreateSecurityManager()
                SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true
                SECURITY_MANAGER
            }
    }

    private fun safeCreateSecurityManager(): ClassContextSecurityManager? {
        return try {
            ClassContextSecurityManager()
        } catch (sm: java.lang.SecurityException) {
            null
        }
    }// Advance until Util is found

    // trace[i] = Util; trace[i+1] = caller; trace[i+2] = caller's caller
    /**
     * Returns the name of the class which called the invoking method.
     *
     * @return the name of the class which called the invoking method.
     */
    fun getCallingClass(): Class<*>? {
        val securityManager : ClassContextSecurityManager = getSecurityManager() ?: return null
        val trace: Array<Class<*>> = securityManager.getClassContext()
        val thisClassName: String = Util::class.java.getName()

        // Advance until Util is found
        var i: Int
        i = 0
        while (i < trace.size) {
            if (thisClassName == trace[i].getName()) break
            i++
        }

        // trace[i] = Util; trace[i+1] = caller; trace[i+2] = caller's caller
        check(!(i >= trace.size || i + 2 >= trace.size)) { "Failed to find org.slf4j.helpers.Util or its caller in the stack; " + "this should not happen" }
        return trace[i + 2]
    }

    /*fun getCallingKlass(): KClass<*>? {
        val securityManager = securityManager ?: return null
        val trace: Array<KClass<*>> = securityManager.getClassContext()
        val thisClassName: String = Util::class.java.getName()

        // Advance until Util is found
        var i: Int
        i = 0
        while (i < trace.size) {
            if (thisClassName == trace[i].qualifiedName) break
            i++
        }

        // trace[i] = Util; trace[i+1] = caller; trace[i+2] = caller's caller
        check(!(i >= trace.size || i + 2 >= trace.size)) { "Failed to find org.slf4j.helpers.Util or its caller in the stack; " + "this should not happen" }
        return trace[i + 2]
    }*/

    actual fun report(msg: String?, t: Throwable) {
        java.lang.System.err.println(msg)
        java.lang.System.err.println("Reported exception:")
        t.printStackTrace()
    }

    actual fun report(msg: String) {
        java.lang.System.err.println("SLF4J: $msg")
    }

    /**
     * In order to call [SecurityManager.getClassContext], which is a
     * protected method, we add this wrapper which allows the method to be visible
     * inside this package.
     */
    private class ClassContextSecurityManager : java.lang.SecurityManager() {
        public override fun getClassContext(): Array<Class<*>> {
            return super.getClassContext()
        }
    }
}