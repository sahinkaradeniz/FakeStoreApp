package com.skapps.fakestoreapp.core.loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A thread-safe implementation of the [GlobalLoadingManager] interface.
 * It manages three different types of loading states using a reference counting strategy:
 * 1) [LoadingType.Global] - represents a full-screen (global) loading state.
 * 2) [LoadingType.Local] - represents a loading state for a specific component, identified by a key.
 * 3) [LoadingType.Partial] - represents a partial loading state (for example, a popup or a specific section of the UI).
 *
 * Each type is tracked via separate counters/maps. The manager uses a [Mutex] to ensure
 * thread-safe increments and decrements. A [StateFlow] publishes all currently active loading states.
 */
@Singleton
class GlobalLoadingManagerImpl @Inject constructor() : GlobalLoadingManager {

    /**
     * A [Mutex] to guarantee thread-safe read/write operations on counters.
     */
    private val mutex = Mutex()

    /**
     * Counter for [LoadingType.Global]. Multiple increments mean multiple global loading
     * requests are active, and the global loading will not be dismissed until all are closed.
     */
    private var globalCount: Int = 0

    /**
     * A map of keys to counters for [LoadingType.Local]. Each key may have multiple loading
     * requests, and won't be dismissed until its counter returns to zero.
     */
    private val localMap = mutableMapOf<String, Int>()

    /**
     * A map of keys to counters for [LoadingType.Partial]. Similar to Local, but represents
     * partial (or section/popup) loading states.
     */
    private val partialMap = mutableMapOf<String, Int>()

    /**
     * A [StateFlow] representing the set of currently active loading states (Global, Local, or Partial).
     */
    private val _activeLoadings = MutableStateFlow<Set<LoadingType>>(emptySet())
    override val activeLoadings: StateFlow<Set<LoadingType>> = _activeLoadings

    /**
     * Increments the corresponding counter for the specified [type].
     * If [type] is already active, this increases its count.
     *
     * @param type The loading type to show (Global, Local, or Partial).
     */
     override suspend fun show(type: LoadingType) {
        mutex.withLock {
            when (type) {
                is LoadingType.Global -> {
                    globalCount++
                }
                is LoadingType.Local -> {
                    val current = localMap[type.key] ?: 0
                    localMap[type.key] = current + 1
                }
                is LoadingType.Partial -> {
                    val current = partialMap[type.message] ?: 0
                    partialMap[type.message] = current + 1
                }
            }
            updateFlowLocked()
        }
    }

    /**
     * Decrements the corresponding counter for the specified [type].
     * If the counter drops to zero for Local or Partial, it will be removed
     * from the active set. If Global drops to zero, the global loading state
     * is deactivated.
     *
     * @param type The loading type to hide (Global, Local, or Partial).
     */
    override suspend fun hide(type: LoadingType) {
        mutex.withLock {
            when (type) {
                is LoadingType.Global -> {
                    if (globalCount > 0) {
                        globalCount--
                    }
                }
                is LoadingType.Local -> {
                    val current = localMap[type.key] ?: 0
                    if (current > 0) {
                        localMap[type.key] = current - 1
                    }
                }
                is LoadingType.Partial -> {
                    val current = partialMap[type.message] ?: 0
                    if (current > 0) {
                        partialMap[type.message] = current - 1
                    }
                }
            }
            updateFlowLocked()
        }
    }

    /**
     * Updates the [_activeLoadings] flow based on the current counters of Global, Local, and Partial.
     * This method is always called within a locked block, ensuring thread safety.
     */
    private fun updateFlowLocked() {
        val newSet = mutableSetOf<LoadingType>()
        println("Global count: $globalCount")
        // Check if Global loading is active
        if (globalCount > 0) {
            println("Global loading is active")
            newSet.add(LoadingType.Global)
        }

        // Check each Local key
        for ((key, count) in localMap) {
            if (count > 0) {
                println("Local loading is active for key: $key")
                newSet.add(LoadingType.Local(key))
            }
        }

        // Check each Partial key
        for ((key, count) in partialMap) {
            if (count > 0) {
                println("Partial loading is active for key: $key")
                newSet.add(LoadingType.Partial(key))
            }
        }
        println("Active loadings: $newSet")
        _activeLoadings.value = newSet
    }
}