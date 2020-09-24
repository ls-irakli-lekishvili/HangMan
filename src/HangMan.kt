import java.util.*
import kotlin.collections.ArrayList

class HangMan(private val name: String, private val word: String, private val lives: Int = 8) {
    private var record: Int = lives;
    private var progress: String = "_".repeat(word.length);
    private var allCharacters = "";
    private var isWinner: Boolean = false;


    fun startGame() {
        while (lives != 0 && !progress.equals(word, ignoreCase = true)) {
            printer(PrintCodes.INPUT);
            val character = readLine().toString();

            if (validateCharacter(character)) {
                if (allCharacters.contains(character)) {
                    printer(PrintCodes.REPEAT, record.toString());
                    continue;
                } else {
                    allCharacters += character;
                }

                if (word.contains(character)) {
                    modifyWord(character);
                    printer(PrintCodes.SUCCESS, progress);
                } else {
                    record--;
                    printer(PrintCodes.FAIL, record.toString());
                }

            } else {
                printer(PrintCodes.ERROR);
            }
        }

        isWinner = lives != 0;
    }

    private fun modifyWord(character: String) {
        var currentProgress: String = "";
        for (index in word.indices) {
            currentProgress += when {
                word[index].equals(character[0], ignoreCase = true) -> {
                    character[0].toLowerCase();
                }
                progress[index] != '_' -> {
                    progress[index];
                }
                else -> {
                    '_';
                }
            }
        }
        progress = currentProgress;

    }

    private fun printer(message: PrintCodes, additionalInfo: String = "") {
        when (message) {
            PrintCodes.INPUT -> {
                print("Enter Character: ");
            }
            PrintCodes.SUCCESS -> {
                println("Yes, it is there!!!");
                println("Current Word is: $additionalInfo");
            }
            PrintCodes.FAIL -> {
                println("There is no such character");
                println("Lives remaining: $additionalInfo");
            }
            PrintCodes.ERROR -> {
                println("Please, enter a valid character!")
            }
            PrintCodes.REPEAT -> {
                println("You already tried this character");
                println("Lives remaining: $additionalInfo");
            }
        }
    }


    private fun validateCharacter(character: String): Boolean {
        val regex = "^[a-zA-Z]$".toRegex();
        if (regex.matches(character)) {
            return true;
        }
        return false;
    }

    fun isWinner(): Boolean {
        return isWinner;
    }

    fun getWord(): String {
        return word;
    }

    fun getRecord(): String? {
        return if (isWinner) "$name - $record Lives" else null;
    }
}

fun main() {
    var winners: MutableList<HangMan> = ArrayList<HangMan>();

    var input: String = "";
    while (!input.equals("n", ignoreCase = true)) {
        input = readLine().toString();

        when (input) {
            "N", "n" -> {
                println("Thanks for playing!");
            }

            "Y", "y" -> {
                print("Enter Name: ")
                val name = readLine().toString();
                println("Hello $name, Let’s play Hangman!")
                print("Enter Word: ")
                val word = readLine().toString();
                val game = HangMan(name, word);
                game.startGame();

                if (game.isWinner()) {
                    println("Congratulations! you won");
                    winners.add(game);
                } else {
                    println("Sorry, you lost… The word was: ${game.getWord()}")
                }
            }
        }
    }
    print(winners[0].getRecord())

}