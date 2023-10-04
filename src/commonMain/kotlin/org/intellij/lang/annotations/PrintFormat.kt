/*
 * Copyright 2000-2021 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.intellij.lang.annotations

/**
 * Specifies that the method parameter is a printf-style print format pattern,
 * as described in Java's [`Formatter`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Formatter.html).
 *
 * Code editors that support [Pattern] annotation will check
 * the syntax of this const value automatically. It could also be especially recognized to
 * check whether the subsequent var-arg arguments match the expected arguments
 * mentioned in the pattern. E. g., consider that the following method is annotated:
 *
 * `void myprintf(@PrintFormat String format, Object... args) {...}`
 *
 * In this case, code editors might recognize that the following call is erroneous,
 * and issue a warning:
 *
 * `myprintf("%d\n", "hello"); // warning: a number expected instead of "hello"`
 *
 * @see Pattern
 */
@MustBeDocumented
@Pattern(PRINT_FORMAT)
annotation class PrintFormat

// %[argument_index$][flags][width][.precision]conversion
// Expression is taken from java.util.Formatter.fsPattern
@Language("RegExp")
private const val ARG_INDEX = "(?:\\d+\\$)?"

@Language("RegExp")
private const val FLAGS = "(?:[-#+ 0,(<]*)?"

@Language("RegExp")
private const val WIDTH = "(?:\\d+)?"

@Language("RegExp")
private const val PRECISION = "(?:\\.\\d+)?"

@Language("RegExp")
private const val CONVERSION = "(?:[tT])?(?:[a-zA-Z%])"

@Language("RegExp")
private const val TEXT = "[^%]|%%"

@Language("RegExp")
internal const val PRINT_FORMAT = "(?:$TEXT|(?:%$ARG_INDEX$FLAGS$WIDTH$PRECISION$CONVERSION))*"