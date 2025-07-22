package miscellaneous.redisPartitioning

import miscellaneous.redisPartitioning.RedisPartitionReassigner.Companion.MAX_IMBALANCE
import kotlin.math.max
import kotlin.math.min

object RedisPartitionAssignmentValidator {
    fun validateAssignments(assignments: PartitionAssignments, numPartitions: Int, hosts: Set<Host>, description: String) {
        val partitionToHost = buildPartitionToHostMap(assignments)

        validateAllPartitionsAssigned(partitionToHost, numPartitions, description)
        validateUsedHostsMatchAvailable(partitionToHost, hosts, description)
        validateBalancing(assignments, description)
    }

    fun validateAllPartitionsAssigned(assignments: PartitionAssignments, numPartitions: Int, description: String) =
        validateAllPartitionsAssigned(buildPartitionToHostMap(assignments), numPartitions, description)

    fun validateAllPartitionsAssigned(partitionToHost: Map<Int, Host>, numPartitions: Int, description: String) {
        val assignedPartitions = partitionToHost.keys
        val expectedPartitions = (0 until numPartitions).toSet()

        val missingPartitions = expectedPartitions - assignedPartitions
        val outOfRangePartitions = expectedPartitions - assignedPartitions

        if (missingPartitions.isNotEmpty() || outOfRangePartitions.isNotEmpty()) {
            val missingPartitionsMsg = missingPartitions.joinToString { "\n\t- Missing partition: $it" }
            val outOfRangePartitionsMsg = outOfRangePartitions.joinToString { "\n\t- Out of range partition: $it" }
            throw IllegalStateException(
                "Expected all partitions from 0 to $numPartitions (excluded) to be assigned, but the following issues were found for $description:" +
                        "$missingPartitionsMsg$outOfRangePartitionsMsg"
            )
        }
    }

    private fun validateUsedHostsMatchAvailable(partitionToHost: Map<Int, Host>, hosts: Set<Host>, description: String) {
        val usedHosts = partitionToHost.values.toSet()
        val unknownHosts = usedHosts - hosts
        val unusedHosts = hosts - usedHosts

        if (unknownHosts.isNotEmpty() || unusedHosts.isNotEmpty()) {
            val unknownHostsMsg = unknownHosts.joinToString { "\n\t- Unknown host with assigned partitions: $it" }
            val unusedHostsMsg = unusedHosts.joinToString { "\n\t- Current host with no assigned partition: $it" }
            throw IllegalStateException(
                "Expected all current hosts to have at least one partition assigned and all hosts with an assigned partition " +
                    "to exist, but the following issues were found for $description:$unknownHostsMsg$unusedHostsMsg"
            )
        }
    }

    private fun validateBalancing(assignments: PartitionAssignments, description: String) {
        var minAssignedCount = Int.MAX_VALUE
        var maxAssignedCount = Int.MIN_VALUE

        assignments.values.forEach { partitions ->
            minAssignedCount = min(minAssignedCount, partitions.size)
            maxAssignedCount = max(maxAssignedCount, partitions.size)
        }

        val maxObservedImbalance = maxAssignedCount - minAssignedCount
        if (maxObservedImbalance > MAX_IMBALANCE) {
            throw IllegalStateException("Assignments for $description are not balanced. The maximum observed imbalance was $maxObservedImbalance but the " +
                    "maximum allowed was $MAX_IMBALANCE")
        }
    }

    private fun buildPartitionToHostMap(assignments: PartitionAssignments): Map<Int, Host> =
        assignments.asSequence().flatMap { (host, partitions) -> partitions.asSequence().map { it to host } }.toMap()
}
