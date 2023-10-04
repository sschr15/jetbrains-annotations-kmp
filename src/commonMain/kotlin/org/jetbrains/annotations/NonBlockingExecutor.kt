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
 * Indicates that the annotated executor (CoroutineContext, Scheduler)
 * does not allow blocking methods execution.
 *
 *
 *
 * If a given executor allows blocking calls, [BlockingExecutor] should be used.
 *
 *
 *
 * Example 1 (Kotlin coroutines):
 * ```
 * class NonBlockingExampleService {
 *     val dispatcher: @NonBlockingExecutor CoroutineContext
 *         get() { ... }
 *
 *     suspend fun foo() {
 *         val result = withContext(dispatcher) {
 *             blockingBuzz() // IDE warning: `Possibly blocking call in non-blocking context`
 *         }
 *     }
 *
 *     @Blocking fun blockingBuzz() { ... }
 * }
 * ```
 *
 * Example 2 (Java with Reactor framework):
 * ```
 * class NonBlockingExampleService {
 *     private static final @NonBlockingExecutor Scheduler operationsScheduler =
 *             Schedulers.newParallel("parallel");
 *
 *     public Flux<String> foo(Flux<String> urls) {
 *         return urls.publishOn(operationsScheduler)
 *                 .filter(url -> blockingBuzz(url) != null);  // IDE warning: `Possibly blocking call in non-blocking context`
 *     }
 *
 *     @Blocking
 *     private String blockingBuzz(String url) { ... }
 * }
 * ```
 *
 * @see Blocking
 *
 * @see NonBlocking
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPE_PARAMETER)
annotation class NonBlockingExecutor
