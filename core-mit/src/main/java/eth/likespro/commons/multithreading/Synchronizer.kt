/*
 * Copyright (c) 2025 likespro
 *
 * From https://github.com/likespro/commons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eth.likespro.commons.multithreading

import kotlin.math.max

/**
 * The [Synchronizer] class provides a mechanism to synchronize the execution of code blocks. All blocks of code executed via the same [Synchronizer] instance will never be running in simultaneously.
 * It uses a lock object to ensure that only one thread can execute the synchronized block at a time.
 * It also tracks the total and maximum time spent in synchronized blocks.
 */
class Synchronizer (
    /**
     * A lock object used for synchronization across threads.
     * This ensures that only one thread can execute the synchronized block at a time.
     */
    private var lock: Any = Any()
) {
    /**
     * The total time spent in synchronized blocks.
     */
    var totalWorkedFor = 0L
        private set

    /**
     * The maximum time spent in a single synchronized block.
     */
    var maxWorkedFor = 0L
        private set

    /**
     * Executes the given block of code in a synchronized across all threads manner.
     * It also measures the time taken to execute the block and updates the total and maximum time worked for.
     *
     * @param what The block of code to be executed.
     * @return The result of the block of code.
     */
    fun <R> doSynced(what: () -> R): R{
        val startedAt = System.currentTimeMillis()
        val res = synchronized(lock, what)
        maxWorkedFor = max(maxWorkedFor, System.currentTimeMillis() - startedAt)
        totalWorkedFor += System.currentTimeMillis() - startedAt
        return res
    }
}