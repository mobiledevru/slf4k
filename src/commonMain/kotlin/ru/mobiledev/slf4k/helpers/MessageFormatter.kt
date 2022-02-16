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
package ru.mobiledev.slf4k.helpers

// contributors: lizongbo: proposed special treatment of array parameter values
// Joern Huxhorn: pointed out double[] omission, suggested deep array copy
/**
 * Formats messages according to very simple substitution rules. Substitutions
 * can be made 1, 2 or more arguments.
 *
 *
 *
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;)
</pre> *
 *
 * will return the string "Hi there.".
 *
 *
 * The {} pair is called the *formatting anchor*. It serves to designate
 * the location where arguments need to be substituted within the message
 * pattern.
 *
 *
 * In case your message contains the '{' or the '}' character, you do not have
 * to do anything special unless the '}' character immediately follows '{'. For
 * example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set {1,2,3} is not equal to {}.&quot;, &quot;1,2&quot;);
</pre> *
 *
 * will return the string "Set {1,2,3} is not equal to 1,2.".
 *
 *
 *
 * If for whatever reason you need to place the string "{}" in the message
 * without its *formatting anchor* meaning, then you need to escape the
 * '{' character with '\', that is the backslash character. Only the '{'
 * character should be escaped. There is no need to escape the '}' character.
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set \\{} is not equal to {}.&quot;, &quot;1,2&quot;);
</pre> *
 *
 * will return the string "Set {} is not equal to 1,2.".
 *
 *
 *
 * The escaping behavior just described can be overridden by escaping the escape
 * character '\'. Calling
 *
 * <pre>
 * MessageFormatter.format(&quot;File name is C:\\\\{}.&quot;, &quot;file.zip&quot;);
</pre> *
 *
 * will return the string "File name is C:\file.zip".
 *
 *
 *
 * The formatting conventions are different than those of [MessageFormat]
 * which ships with the Java platform. This is justified by the fact that
 * SLF4J's implementation is 10 times faster than that of [MessageFormat].
 * This local performance difference is both measurable and significant in the
 * larger context of the complete logging processing chain.
 *
 *
 *
 * See also [.format],
 * [.format] and
 * [.arrayFormat] methods for more details.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 */
object MessageFormatter {
    const val DELIM_START = '{'
    const val DELIM_STOP = '}'
    const val DELIM_STR = "{}"
    private const val ESCAPE_CHAR = '\\'

    /**
     * Performs single argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;);
    </pre> *
     *
     * will return the string "Hi there.".
     *
     *
     *
     * @param messagePattern
     * The message pattern which will be parsed and formatted
     * @param arg
     * The argument to be substituted in place of the formatting anchor
     * @return The formatted message
     */
    fun format(messagePattern: String, arg: Any): FormattingTuple {
        return arrayFormat(messagePattern, arrayOf(arg))
    }

    /**
     *
     * Performs a two argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
    </pre> *
     *
     * will return the string "Hi Alice. My name is Bob.".
     *
     * @param messagePattern
     * The message pattern which will be parsed and formatted
     * @param arg1
     * The argument to be substituted in place of the first formatting
     * anchor
     * @param arg2
     * The argument to be substituted in place of the second formatting
     * anchor
     * @return The formatted message
     */
    fun format(messagePattern: String, arg1: Any, arg2: Any): FormattingTuple {
        return arrayFormat(messagePattern, arrayOf(arg1, arg2))
    }

    fun arrayFormat(messagePattern: String?, argArray: Array<Any>): FormattingTuple {
        val throwableCandidate = getThrowableCandidate(argArray)
        var args = argArray
        if (throwableCandidate != null) {
            args = trimmedCopy(argArray)
        }
        return arrayFormat(messagePattern, args, throwableCandidate)
    }

    fun arrayFormat(messagePattern: String?, argArray: Array<Any>?, throwable: Throwable?): FormattingTuple {
        if (messagePattern == null) {
            return FormattingTuple(null, argArray, throwable)
        }
        if (argArray == null) {
            return FormattingTuple(messagePattern)
        }
        var i = 0
        var j: Int
        // use string builder for better multicore performance
        val sbuf = StringBuilder(messagePattern.length + 50)
        var L: Int
        L = 0
        while (L < argArray.size) {
            j = messagePattern.indexOf(DELIM_STR, i)
            i = if (j == -1) {
                // no more variables
                return if (i == 0) { // this is a simple string
                    FormattingTuple(messagePattern, argArray, throwable)
                } else { // add the tail string which contains no variables and return
                    // the result.
                    sbuf.append(messagePattern, i, messagePattern.length)
                    FormattingTuple(sbuf.toString(), argArray, throwable)
                }
            } else {
                if (isEscapedDelimeter(messagePattern, j)) {
                    if (!isDoubleEscaped(messagePattern, j)) {
                        L-- // DELIM_START was escaped, thus should not be incremented
                        sbuf.append(messagePattern, i, j - 1)
                        sbuf.append(DELIM_START)
                        j + 1
                    } else {
                        // The escape character preceding the delimiter start is
                        // itself escaped: "abc x:\\{}"
                        // we have to consume one backward slash
                        sbuf.append(messagePattern, i, j - 1)
                        deeplyAppendParameter(sbuf, argArray[L], HashMap())
                        j + 2
                    }
                } else {
                    // normal case
                    sbuf.append(messagePattern, i, j)
                    deeplyAppendParameter(sbuf, argArray[L], HashMap())
                    j + 2
                }
            }
            L++
        }
        // append the characters following the last {} pair.
        sbuf.append(messagePattern, i, messagePattern.length)
        return FormattingTuple(sbuf.toString(), argArray, throwable)
    }

    fun isEscapedDelimeter(messagePattern: String, delimeterStartIndex: Int): Boolean {
        if (delimeterStartIndex == 0) {
            return false
        }
        val potentialEscape = messagePattern[delimeterStartIndex - 1]
        return potentialEscape == ESCAPE_CHAR
    }

    fun isDoubleEscaped(messagePattern: String, delimeterStartIndex: Int): Boolean {
        return delimeterStartIndex >= 2 && messagePattern[delimeterStartIndex - 2] == ESCAPE_CHAR
    }

    // special treatment of array values was suggested by 'lizongbo'
    private fun deeplyAppendParameter(sbuf: StringBuilder, o: Any?, seenMap: MutableMap<Array<Any>, Any?>) {
        when(o) {
            null -> sbuf.append("null")
            is BooleanArray -> booleanArrayAppend(sbuf, o)
            is ByteArray -> byteArrayAppend(sbuf, o)
            is CharArray -> charArrayAppend(sbuf, o)
            is ShortArray -> shortArrayAppend(sbuf, o)
            is IntArray -> intArrayAppend(sbuf, o)
            is LongArray -> longArrayAppend(sbuf, o)
            is FloatArray -> floatArrayAppend(sbuf, o)
            is DoubleArray -> doubleArrayAppend(sbuf, o)
            is Array<*> -> objectArrayAppend(sbuf, o as Array<Any>, seenMap)
            else -> safeObjectAppend(sbuf, o)
        }
    }

    private fun safeObjectAppend(sbuf: StringBuilder, o: Any) {
        try {
            val oAsString = o.toString()
            sbuf.append(oAsString)
        } catch (t: Throwable) {
            ru.mobiledev.slf4k.helpers.Util.report(
                "SLF4J: Failed toString() invocation on an object of type [" + o.javaClass.getName() + "]",
                t
            )
            sbuf.append("[FAILED toString()]")
        }
    }

    private fun objectArrayAppend(sbuf: StringBuilder, a: Array<Any>, seenMap: MutableMap<Array<Any>, Any?>) {
        sbuf.append('[')
        if (!seenMap.containsKey(a)) {
            seenMap[a] = null
            val len = a.size
            for (i in 0 until len) {
                deeplyAppendParameter(sbuf, a[i], seenMap)
                if (i != len - 1) sbuf.append(", ")
            }
            // allow repeats in siblings
            seenMap.remove(a)
        } else {
            sbuf.append("...")
        }
        sbuf.append(']')
    }

    private fun booleanArrayAppend(sbuf: StringBuilder, a: BooleanArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun byteArrayAppend(sbuf: StringBuilder, a: ByteArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i].toInt())
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun charArrayAppend(sbuf: StringBuilder, a: CharArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun shortArrayAppend(sbuf: StringBuilder, a: ShortArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i].toInt())
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun intArrayAppend(sbuf: StringBuilder, a: IntArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun longArrayAppend(sbuf: StringBuilder, a: LongArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun floatArrayAppend(sbuf: StringBuilder, a: FloatArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun doubleArrayAppend(sbuf: StringBuilder, a: DoubleArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1) sbuf.append(", ")
        }
        sbuf.append(']')
    }

    /**
     * Helper method to determine if an [Object] array contains a [Throwable] as last element
     *
     * @param argArray
     * The arguments off which we want to know if it contains a [Throwable] as last element
     * @return if the last [Object] in argArray is a [Throwable] this method will return it,
     * otherwise it returns null
     */
    fun getThrowableCandidate(argArray: Array<out Any>): Throwable? {
        if (argArray == null || argArray.size == 0) {
            return null
        }
        val lastEntry = argArray[argArray.size - 1]
        return if (lastEntry is Throwable) {
            lastEntry
        } else null
    }

    /**
     * Helper method to get all but the last element of an array
     *
     * @param argArray
     * The arguments from which we want to remove the last element
     *
     * @return a copy of the array without the last element
     */
    fun trimmedCopy(argArray: Array<out Any>): Array<Any> {
        check(!(argArray == null || argArray.size == 0)) { "non-sensical empty or null argument array" }
        val trimmedLen = argArray.size - 1
        val trimmed = arrayOfNulls<Any>(trimmedLen)
        if (trimmedLen > 0) {
            java.lang.System.arraycopy(argArray, 0, trimmed, 0, trimmedLen)
        }
        return trimmed
    }
}