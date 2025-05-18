package eth.likespro.commons.multithreading

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SynchronizerTest {
    @Test
    fun doSynced_executesBlockAndReturnsResult() {
        val synchronizer = Synchronizer()
        val result = synchronizer.doSynced { "Success" }
        assertEquals("Success", result)
    }

    @Test
    fun doSynced_updatesTotalWorkedForCorrectly() {
        val synchronizer = Synchronizer()
        synchronizer.doSynced { Thread.sleep(50) }
        assertTrue(synchronizer.totalWorkedFor >= 50)
    }

    @Test
    fun doSynced_updatesMaxWorkedForCorrectly() {
        val synchronizer = Synchronizer()
        synchronizer.doSynced { Thread.sleep(30) }
        synchronizer.doSynced { Thread.sleep(50) }
        assertTrue(synchronizer.maxWorkedFor >= 50)
    }

    @Test
    fun doSynced_handlesConcurrentExecution() {
        val synchronizer = Synchronizer()
        val threads = List(10) {
            Thread {
                synchronizer.doSynced { Thread.sleep(10) }
            }
        }
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        assertTrue(synchronizer.totalWorkedFor >= 100)
    }

    @Test
    fun doSynced_usesCustomLockObject() {
        val customLock = Any()
        val synchronizer = Synchronizer(customLock)
        val result = synchronized(customLock) {
            synchronizer.doSynced { "Locked" }
        }
        assertEquals("Locked", result)
    }

    @Test fun doSynced_stressTest() {
        val synchronizer = Synchronizer()
        var counter = 0
        repeat(10000) {
            Thread {
                synchronizer.doSynced { counter++ }
            }.start()
        }
        Thread.sleep(1000) // Wait for all threads to finish
        assertEquals(counter, 10000)
    }
}