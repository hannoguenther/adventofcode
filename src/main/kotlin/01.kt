import java.io.File

fun day1() {
    val elvesTotalCalories: List<Int> = File("inputs/01.txt")
        .readText()
        .split("\n\n")
        .map { elfSnacksText: String ->
            elfSnacksText.lines().sumOf { it.toInt() }
        }

    // task 1
    val highestTotalCalories = elvesTotalCalories.max()
    println("elf with highest total calories: $highestTotalCalories")

    // task 2
    val highestTotalCaloriesOfTop3Elves = elvesTotalCalories.sortedDescending().take(3).sum()
    println("sum of top 3 highest total elves calories: $highestTotalCaloriesOfTop3Elves")
}