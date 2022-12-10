package day07

object FileSystem {
    sealed interface Entry {
        val contentSize: Long
    }

    data class Directory(val name: String = "/", val parent: Directory? = null) : Entry {
        private val entryMap = mutableMapOf<String, Entry>()

        private val entries: Collection<Entry>
            get() = entryMap.values

        val recursiveEntries: Sequence<Entry> = sequence {
            entries.forEach {
                yield(it)
                if (it is Directory) yieldAll(it.recursiveEntries)
            }
        }

        fun childDir(name: String): Directory {
            return entryMap.getOrPut(name) { Directory(name, this) } as Directory
        }

        fun childFile(size: Long, name: String): File {
            return entryMap.getOrPut(name) { File(size, name) } as File
        }

        // is cached when requested - so entries should not change afterwards!
        override val contentSize: Long by lazy {
            entries.sumOf { it.contentSize }
        }
    }

    data class File(override val contentSize: Long, val name: String) : Entry

    fun parseRootFromTerminalOutput(): Directory {
        val root = Directory()

        var currentDir: Directory = root
        var currentCommand: String? = null
        java.io.File("src/day07/input.txt").forEachLine { line ->
            val s = line.split(' ')

            if (s[0] == "\$") {
                currentCommand = s[1]
                if (currentCommand == "cd") {
                    currentDir = when (val arg = s[2]) {
                        "/" -> root
                        ".." -> currentDir.parent ?: return@forEachLine
                        else -> currentDir.childDir(arg)
                    }
                }
            } else if (currentCommand == "ls") {
                if (s[0] == "dir") {
                    currentDir.childDir(s[1])
                } else {
                    currentDir.childFile(size = s[0].toLong(), name = s[1])
                }
            }
        }

        return root
    }
}