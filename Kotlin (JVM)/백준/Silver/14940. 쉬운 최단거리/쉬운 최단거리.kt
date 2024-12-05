class PriorityQueue<T>(e : T, c : (T, T) -> Boolean) {
    var tree : ArrayList<T> = arrayListOf(e)
    var compare : (T, T) -> Boolean = c
    fun add(v : T) {
        tree.add(v)
        var index = tree.size - 1
        while (index > 1) {
            if (compare(tree[index], tree[index/2])) {
                tree[index] = tree[index/2].also { tree[index/2] = tree[index] }
            }
            index /= 2
        }
    }
    fun poll() : T {
        val result = tree[1]
        tree[1] = tree.last()
        tree.removeLast()
        var index = 1
        while (index < tree.size) {
            val l = index * 2
            val r = l + 1
            var swapIndex = index
            if (l < tree.size && compare(tree[l], tree[swapIndex])) swapIndex = l
            if (r < tree.size && compare(tree[r], tree[swapIndex])) swapIndex = r
            if (swapIndex == index) break
            tree[swapIndex] = tree[index].also { tree[index] = tree[swapIndex] }
            index = swapIndex
        }
        return result
    }
    fun isNotEmpty() : Boolean { return tree.size != 1 }
}

fun dijkstra(input : Array<IntArray>, start : IntArray, size : IntArray) : Array<IntArray> {
    val delta = arrayOf(
        intArrayOf(1, 0),
        intArrayOf(0, 1),
        intArrayOf(0, -1),
        intArrayOf(-1, 0)
    )

    val (n, m) = size
    val visit = Array(n) {IntArray(m)}
    val output = Array(n) {IntArray(m)}

    val pq = PriorityQueue(IntArray(0)) {a, b -> a[0] < b[0]}
    pq.add(intArrayOf(0, start[0], start[1]))
    visit[start[0]][start[1]] = 1
    while (pq.isNotEmpty()) {
        val (dist, y, x) = pq.poll()
        output[y][x] = dist
        for ((xx, yy) in delta) {
            val newX = x + xx
            val newY = y + yy
            if (newY < 0 || n <= newY) continue
            if (newX < 0 || m <= newX) continue
            if (visit[newY][newX] == 1) continue
            if (input[newY][newX] != 1) continue
            pq.add(intArrayOf(dist + 1, newY, newX))
            visit[newY][newX] = 1
        }
    }

    for (y in 0 until n)
        for (x in 0 until m) {
            if (visit[y][x] == 1) continue
            if (input[y][x] != 1) continue
            output[y][x] = -1
        }
    return output
}

fun main(args: Array<String>) {
    val size = readln().split(' ').map(String::toInt).toIntArray()
    val (n, m) = size
    val input = Array(n){IntArray(0)}
    var start = IntArray(2)
    for(i in 0 until n) {
        input[i] = readln()
            .split(' ')
            .map(String::toInt)
            .mapIndexed { j, v -> if (v == 2) start = intArrayOf(i, j); v}
            .toIntArray()
    }

    val output = dijkstra(input, start, size)
    for (line in output) {
        println(line.joinToString(" "))
    }
}