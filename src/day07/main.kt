package day07

fun main() {
    val root: FileSystem.Directory = FileSystem.parseRootFromTerminalOutput()

    val allDirectories: Sequence<FileSystem.Directory> = root.recursiveEntries.filterIsInstance<FileSystem.Directory>() + root


    val deleteCandidateDirectoriesSize = allDirectories
        .filter { it.contentSize <= 100_000L }
        .sumOf { it.contentSize }
    println("sum of directories size at most 100000: $deleteCandidateDirectoriesSize")


    val diskSpace = object {
        val total = 70_000_000L
        val requiredForUpdates = 30_000_000L
        val used = root.contentSize
    }
    val bestDeleteCandidateDirectorySize = allDirectories.sortedBy { it.contentSize }.first {
        diskSpace.used - it.contentSize + diskSpace.requiredForUpdates <= diskSpace.total
    }.contentSize
    println("smallest directory size to delete in order to free up enough space : $bestDeleteCandidateDirectorySize")
}