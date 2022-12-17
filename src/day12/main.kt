package day12


fun main() {

    fun dijkstraShortestPath(origin: Node, isDestination: (Node) -> Boolean, reachableNeighbours: (Node) -> List<Node>): Int {
        val visited = mutableSetOf<Node>()
        val distances = mutableMapOf<Node, Int>()

        fun Node.distance() = distances.getOrDefault(this, Int.MAX_VALUE)

        tailrec fun shortestPath(current: Node): Int {
            val currentDistance = current.distance()
            if (isDestination(current)) return currentDistance

            visited.add(current)
            distances.remove(current)

            for (neighbour in reachableNeighbours(current) - visited) {
                distances[neighbour] = neighbour.distance().coerceAtMost(currentDistance + 1)
            }

            return shortestPath(distances.minBy { it.value }.key)
        }

        distances[origin] = 0
        return shortestPath(origin)
    }


    val graph = Graph.parse()

    fun climbUpNeighbours(node: Node): List<Node> = graph.neighbours(node).filter {
        it.elevation <= node.elevation + 1
    }

    val shortestPathFromS2E = dijkstraShortestPath(graph.start, Node::isEnd, ::climbUpNeighbours)
    println("length of shortest path from S to E: $shortestPathFromS2E")


    fun climbDownNeighbours(node: Node): List<Node> = graph.neighbours(node).filter {
        node.elevation <= it.elevation + 1
    }

    val shortestPathFromE2a = dijkstraShortestPath(graph.end, Node::isLowestElevation, ::climbDownNeighbours)
    println("length of shortest path from a to E: $shortestPathFromE2a")
}