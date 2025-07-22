package miscellaneous.redisPartitioning

import miscellaneous.redisPartitioning.RedisPartitionAssignmentValidator.validateAllPartitionsAssigned
import miscellaneous.redisPartitioning.RedisPartitionAssignmentValidator.validateAssignments
import java.util.Deque
import java.util.ArrayDeque
import java.util.NavigableMap
import java.util.SortedSet
import java.util.TreeMap
import java.util.TreeSet

typealias PartitionAssignments = NavigableMap<Host, List<Int>>

/**
 * This class manages the assignment of data partitions between a fleet of hosts and is able to shuffle partitions around
 * to support the addition and removal of hosts between two assignment periods, while minimizing the amount of data being displaced.
 */
class RedisPartitionReassigner(private val numPartitions: Int) {
    companion object {
        const val MAX_IMBALANCE = 1
    }

    fun updateAssignments(assignments: PartitionAssignments, currentHosts: List<Host>): PartitionAssignments {
        // We validate that current assignments have exactly the same number of partitions as the new one we'll construct because
        // we do not support shuffling to a smaller or larger number of partitions
        validateAllPartitionsAssigned(assignments, numPartitions, "current assignments")

        val maxPartition = assignments.values.asSequence()
            .flatMap { it.asSequence() }
            .max()

        if (maxPartition >= numPartitions) {
            throw IllegalArgumentException(
                "Current assignments have up to $maxPartition but we expected at most ${numPartitions + 1}. " +
                "We do not support"
            )
        }

        val previousHosts = assignments.keys
        if (previousHosts == currentHosts) return assignments

        val lostHosts = previousHosts - currentHosts
        val addedHosts = currentHosts - previousHosts

        val lostPartitions = getLostPartitions(assignments, lostHosts)
        val hostPartitionAssignments = createHostPartitionAssignments(assignments, lostHosts, addedHosts)

        assignLostPartitions(hostPartitionAssignments, lostPartitions)
        balance(hostPartitionAssignments)

        val newAssignments = toAssignmentMap(hostPartitionAssignments)
        validateAssignments(newAssignments, numPartitions, currentHosts.toSet(), "new assignments")

        return newAssignments
    }

    private fun getLostPartitions(assignments: PartitionAssignments, lostHosts: Set<Host>): Set<Int> {
        return lostHosts.asSequence().flatMap { assignments[it]!!.asSequence() }.toSortedSet()
    }

    private fun createHostPartitionAssignments(
        assignments: PartitionAssignments, lostHosts: Set<Host>, addedHosts: List<Host>
    ): SortedSet<HostPartitionAssignment> {

        val hostAssignments = TreeSet<HostPartitionAssignment>()
        addedHosts.forEach { hostAssignments.add(HostPartitionAssignment(it, ArrayDeque())) }
        assignments.forEach { host, partitions ->
            if (host !in lostHosts) hostAssignments.add(HostPartitionAssignment(host, ArrayDeque(partitions)))
        }

        return hostAssignments
    }

    private fun assignLostPartitions(hostPartitionAssignments: SortedSet<HostPartitionAssignment>, lostPartitions: Set<Int>) {
        lostPartitions.forEach { partition ->
            val leastFull = hostPartitionAssignments.first()
            hostPartitionAssignments.remove(leastFull)
            hostPartitionAssignments.add(leastFull.copyAndPush(partition))
        }
    }

    private fun balance(hostPartitionAssignments: SortedSet<HostPartitionAssignment>) {
        while (hostPartitionAssignments.last().partitions.size - hostPartitionAssignments.first().partitions.size > MAX_IMBALANCE) {
            val leastFull = hostPartitionAssignments.first()
            val mostFull = hostPartitionAssignments.last()

            hostPartitionAssignments.remove(leastFull)
            hostPartitionAssignments.remove(mostFull)

            val partition = mostFull.partitions.last

            hostPartitionAssignments.add(mostFull.copyAndPop())
            hostPartitionAssignments.add(leastFull.copyAndPush(partition))
        }
    }

    private fun toAssignmentMap(hostPartitionAssignments: SortedSet<HostPartitionAssignment>): PartitionAssignments {
        val newAssignments = TreeMap<Host, MutableList<Int>>()

        for (assignment in hostPartitionAssignments) {
            for (partition in assignment.partitions) {
                newAssignments.computeIfAbsent(assignment.host) { mutableListOf() }.add(partition)
            }
        }

        // Not sure if there's a better way to upcast the MutableList as List without losing the TreeMap type or copying data. In my opinion the
        // compiler should support covariance...
        return newAssignments as PartitionAssignments
    }
}

// not that the use of an ordered (not sorted, *ordered*) data structure is crucial here to minimize the amount of shuffle involved when
// several hosts are replaced at different times in quick succession
private data class HostPartitionAssignment(val host: Host, val partitions: Deque<Int>) : Comparable<HostPartitionAssignment> {
    fun copyAndPop(): HostPartitionAssignment {
        val newPartitions = copyPartitions(partitions)
        newPartitions.removeLast()
        return HostPartitionAssignment(host, newPartitions)
    }

    fun copyAndPush(partition: Int): HostPartitionAssignment {
        val newPartitions = copyPartitions(partitions)
        newPartitions.addLast(partition)
        return HostPartitionAssignment(host, newPartitions)
    }

    // Immutability makes the algorithm safer since we use the size of this data structure in the comparator of a sorted set, and it won't cost
    // much in memory or allocation time because the number of partitions will typically remain fairly small. As an optimization, we could implement
    // our own deque as an immutable, persistent data structure (or depend on kotlinx).
    private fun copyPartitions(partitions: Deque<Int>) = ArrayDeque(partitions)

    override fun compareTo(other: HostPartitionAssignment) = compareValuesBy(this, other, { it.partitions.size }, { it.host })
}

data class Host(val hostname: String, val port: Int) : Comparable<Host> {
    override fun compareTo(other: Host): Int = compareValuesBy(this, other, { it.hostname }, { it.port })
}