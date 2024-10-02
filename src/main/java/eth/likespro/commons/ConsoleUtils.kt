package eth.likespro.commons

class ConsoleUtils {
    companion object{
        val ANSI_RESET = "\u001B[0m"
        val ANSI_BLACK = "\u001B[30m"
        val ANSI_RED = "\u001B[31m"
        val ANSI_GREEN = "\u001B[32m"
        val ANSI_YELLOW = "\u001B[33m"
        val ANSI_BLUE = "\u001B[34m"
        val ANSI_PURPLE = "\u001B[35m"
        val ANSI_CYAN = "\u001B[36m"
        val ANSI_WHITE = "\u001B[37m"

        fun progressIndicator(size: Long, progress: Long, total: Long, completeSymbol: Char = '=', processingSymbol: Char = '>', unprocessedSymbol: Char = '.'): String{
            var res = ""
            val complete = progress*size/total
            (0..<complete).forEach { res+=completeSymbol }
            if(complete < size) { res+=processingSymbol }
            (0..<(size-complete-1)).forEach { res+=unprocessedSymbol }
            return res
        }
        fun colorIndicator(progress: Long, total: Long): String{
            val percentage = progress*100/total
            return if(percentage < 50)ANSI_GREEN
            else if(percentage < 90)ANSI_YELLOW
            else ANSI_RED
        }
        fun clearConsole(windowsMethod: Boolean = true){
            when(windowsMethod){
                true -> ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
                false -> (0..100).forEach { _ -> println() }
            }
        }
    }
}