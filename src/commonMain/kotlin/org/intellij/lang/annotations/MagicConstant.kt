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

import org.jetbrains.annotations.NonNls
import kotlin.reflect.KClass

/**
 *
 * This annotation intended to help IntelliJ IDEA and other IDEs to detect and auto-complete int and String constants used as an enumeration.
 * For example, in the [java.awt.Label.Label] constructor the `**alignment**` parameter can be one of the following
 * int constants: [java.awt.Label.LEFT], [java.awt.Label.CENTER] or [java.awt.Label.RIGHT]
 *
 *
 * So, if `@MagicConstant` annotation applied to this constructor, the IDE will check the constructor usages for the allowed values.
 *
 * E.g.
 * `new Label("text", 0); // 0 is not allowed
 * new Label("text", Label.LEFT); // OK
` *
 *
 *
 *
 * `@MagicConstant` can be applied to:
 *
 *  -  Field, local variable, parameter.
 *
 * E.g. 
 * `@MagicConstant(intValues = {TOP, CENTER, BOTTOM})
 * int textPosition;
` *
 * The IDE will check expressions assigned to the variable for allowed values:
 * `textPosition = 0; // not allowed
 * textPosition = TOP; // OK
` *
 *
 *  -  Method
 *
 * E.g.
 * `@MagicConstant(flagsFromClass = java.lang.reflect.Modifier.class)
 * public native int getModifiers();
` *
 *
 * The IDE will analyse getModifiers() method calls and check if its return value is used with allowed values:
 * `if (aClass.getModifiers() == 3) // not allowed
 * if (aClass.getModifiers() == Modifier.PUBLIC) // OK
` *
 *
 *  - Annotation class
 * Annotation class annotated with `@MagicConstant` created alias you can use to annotate
 * everything as if it was annotated with `@MagicConstant` itself.
 *
 * E.g.
 * `@MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) `
 * `@interface FontStyle {} `
 *
 * The IDE will check constructs annotated with @FontStyle for allowed values:
 * `@FontStyle int myStyle = 3; // not allowed`
 * `@FontStyle int myStyle = Font.BOLD | Font.ITALIC; // OK`
 *
 *
 *
 * The `@MagicConstant` annotation has SOURCE retention, i.e. it is removed upon compilation and does not create any runtime overhead.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class MagicConstant(
    /**
     * @return int values (typically named constants) which are allowed here.
     * E.g.
     * <pre>
     * `void setConfirmOpenNewProject(@MagicConstant(intValues = {OPEN_PROJECT_ASK, OPEN_PROJECT_NEW_WINDOW, OPEN_PROJECT_SAME_WINDOW})
     * int confirmOpenNewProject);
    ` *
     */
    val intValues: LongArray = [],
    /**
     * @return String values (typically named constants) which are allowed here.
     */
    @NonNls val stringValues: Array<String> = [],
    /**
     * @return allowed int flags (i.e. values (typically named constants) which can be combined with bitwise OR operator (|).
     * The difference from the [.intValues] is that flags are allowed to be combined (via plus:+ or bitwise OR: |) whereas values aren't.
     * The literals "0" and "-1" are also allowed to denote absence and presense of all flags respectively.
     *
     * E.g.
     * <pre>
     * `@MagicConstant(flags = {HierarchyEvent.PARENT_CHANGED,HierarchyEvent.DISPLAYABILITY_CHANGED,HierarchyEvent.SHOWING_CHANGED})
     * int hFlags;
     *
     * hFlags = 3; // not allowed; should be "magic" constant.
     * if (hFlags & (HierarchyEvent.PARENT_CHANGED | HierarchyEvent.SHOWING_CHANGED) != 0); // OK: combined several constants via bitwise OR
    ` *
     */
    val flags: LongArray = [],
    /**
     * @return allowed values which are defined in the specified class static final constants.
     *
     * E.g.
     * <pre>
     * `@MagicConstant(valuesFromClass = Cursor.class)
     * int cursorType;
     *
     * cursorType = 11; // not allowed; should be "magic" constant.
     * cursorType = Cursor.E_RESIZE_CURSOR; // OK: "magic" constant used.
    ` *
     */
    val valuesFromClass: KClass<*> = Unit::class,
    /**
     * @return allowed int flags which are defined in the specified class static final constants.
     * The difference from the [.valuesFromClass] is that flags are allowed to be combined (via plus:+ or bitwise OR: |) whereas values aren't.
     * The literals "0" and "-1" are also allowed to denote absence and presense of all flags respectively.
     *
     * E.g.
     * <pre>
     * `@MagicConstant(flagsFromClass = java.awt.InputEvent.class)
     * int eventMask;
     *
     * eventMask = 10; // not allowed; should be "magic" constant.
     * eventMask = InputEvent.CTRL_MASK | InputEvent.ALT_MASK; // OK: combined several constants via bitwise OR
    ` *
     */
    val flagsFromClass: KClass<*> = Unit::class
)
