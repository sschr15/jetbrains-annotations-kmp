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
 * Specifies the replacement value for non-constant variables and method return values.
 * This may help static analyzers to properly parse the concatenation of several values
 * which is used in @[Language] or [Pattern] context.
 *
 *
 * Example:
 * 
 * ```
 * @Subst("Tahoma")
 * final String font = new JLabel().getFont().getName();
 *
 * @Language("HTML")
 * String message = "<html><span style='font: " + font + "; font-size:smaller'>" + ... + "</span></html>";
 * ```
 *
 *
 *
 * Here the parser assumes that when `font` appears in the concatenation its value is `"Tahoma"`,
 * so it can continue parsing the concatenation.
 *
 *
 * @see Language
 *
 * @see Pattern
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.VALUE_PARAMETER
)
annotation class Subst(val value: String)
