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
 * This annotation assists the 'Data flow to this' feature by describing data flow
 * from the method parameter to the corresponding container (e.g. `ArrayList.add(item)`)
 * or from the container to the method return value (e.g. `Set.toArray()`)
 * or between method parameters (e.g. `System.arraycopy(array1, 0, array2, length)`)
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class Flow(
    /**
     * Denotes the source of the data flow.
     * Allowed values are:
     *
     *  - `THIS_SOURCE` - Means that the data flows from this container.
     * E.g. annotation for java.util.List method get(index) means the method reads contents of list and returns it.
     * `@Flow(source = THIS_SOURCE) T get(int index);`
     *
     *  - `this.`Field name - means that the data flows from this container some (synthetic) field.
     * E.g. annotation for java.util.Map.keySet() method here means that it returns data from the map from the field named "keys".
     * `@Flow(source = "this.keys") Set<K> keySet();`
     *
     * By default, the source() value is:
     *
     *  - [THIS_SOURCE] if the method was annotated, e.g.
     * `@Flow(sourceIsContainer=true, targetIsContainer=true) Object[] Collection.toArray()`
     * Here the annotation tells us that java.util.Collection.toArray() method
     * reads the contents of this collection (source=THIS_SOURCE by default) and passes it outside.
     *
     *  - Corresponding argument if the method parameter was annotated, e.g.
     * `void List.add(@Flow(targetIsContainer=true) E item)`
     * Here the annotation tells us that java.util.List.add(E item) method
     * takes the argument (source="item" by default) and passes it to this collection.
     */
    val source: String = DEFAULT_SOURCE,
    /**
     * `true` if the data source is container and we should track not the expression but its contents.
     * E.g. the java.util.ArrayList constructor takes the collection and stores its contents:
     * ArrayList(`@Flow(sourceIsContainer=true, targetIsContainer=true) Collection<? extends E> collection`)
     * By default it's false.
     */
    val sourceIsContainer: Boolean = false,
    /**
     * Denotes the destination of the data flow.
     * Allowed values are:
     *
     *  - `THIS_TARGET` - Means that the data flows inside this container (of the class the annotated method belongs to).
     * E.g. annotation for java.util.List method add(element) means the method takes the argument and passes it to this collection.
     * `boolean add(@Flow(target=THIS_TARGET, targetIsContainer=true) E element);`
     *
     *  - Parameter name - means the data flows to this parameter.
     * E.g.
     * `void arraycopy(@Flow(sourceIsContainer=true, target="dest", targetIsContainer=true) Object src, int srcPos, Object dest, int destPos, int length)`
     * means that java.lang.System.arraycopy() method takes its first argument and passes it to the "dest" parameter.
     *
     *  - `this.`Field name - means that the data flows to this container in some (synthetic) field.
     * E.g. annotation for java.util.Map.put(key, value) method here means that it takes the argument 'key' and stores the data in some (hidden) field named "keys".
     * `V put(@Flow(target = "this.keys", targetIsContainer=true) K key, V value);`
     *
     * By default, the target() value is:
     *
     *  - [THIS_TARGET] if the parameter was annotated, e.g.
     * `void List.set(int index, @Flow(targetIsContainer=true) E element)`
     * Here the annotation tells us that java.util.List.set(index, element) method
     * reads its second argument 'element' and passes it to this collection (target=THIS_TARGET by default).
     *
     *  - [RETURN_METHOD_TARGET] if the method was annotated, e.g.:
     * `@Flow(sourceIsContainer=true) E List.remove(int index)`
     * Here the annotation tells us that java.util.List.remove(int index) method
     * returns the data from its collection (target=RETURN_METHOD_TARGET by default).
     */
    val target: String = DEFAULT_TARGET,
    /**
     * `true` if the data target is container and we should track not the expression but its contents.
     * E.g. the java.lang.System.arraycopy() method parameter 'dest' is actually an array:
     * `void arraycopy(@Flow(sourceIsContainer=true, target="dest", targetIsContainer=true) Object src, int srcPos, Object dest, int destPos, int length)`
     * By default it's false.
     */
    val targetIsContainer: Boolean = false
) {
    companion object {
        const val DEFAULT_SOURCE =
            "The method argument (if parameter was annotated) or this container (if instance method was annotated)"
        const val THIS_SOURCE = "this"
        const val DEFAULT_TARGET =
            "This container (if the parameter was annotated) or the return value (if instance method was annotated)"
        const val RETURN_METHOD_TARGET = "The return value of this method"
        const val THIS_TARGET = "this"
    }
}
