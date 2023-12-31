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

package org.jetbrains.annotations

/**
 * An annotation which marks a [Collection] or [Map] type
 * as unmodifiable view. A collection or a map is unmodifiable view if any mutator methods
 * (e.g. [MutableCollection.add]) throw exception or have no effect.
 * However, the content of the collection or the map may still be updated by other code.
 *
 * @see Unmodifiable
 *
 * @since 19.0.0
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPE_PARAMETER)
annotation class UnmodifiableView  