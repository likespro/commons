package utils

import eth.likespro.commons.ConsoleUtils
import eth.likespro.commons.Synchronizer
import java.io.FileWriter
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

class Logger(val logFilePath: String, val maxColumns: Int) {

    var log = mutableListOf<String>()
    var logFile: FileWriter
    var totalWrittenToFile: Long = 0L
    var totalWrittenToConsole: Long = 0L
    val synchronizer = Synchronizer()
    init{
        logFile = FileWriter(logFilePath)
    }

    fun c(tag: String, message: String, type: Char = 'C'){
        val color = when(type){
            'E' -> ConsoleUtils.ANSI_RED
            'W' -> ConsoleUtils.ANSI_YELLOW
            'I' -> ConsoleUtils.ANSI_CYAN
            'D' -> ConsoleUtils.ANSI_BLUE
            else -> ConsoleUtils.ANSI_GREEN
        }
        synchronizer.doSynced { rawLog(message, ConsoleUtils.ANSI_GREEN+"C ", "$tag $color$type -> ") }
    }

    fun d(tag: String, message: String){
        synchronizer.doSynced { rawLog(message, ConsoleUtils.ANSI_BLUE+"D ", "$tag -> ") }
    }
    fun i(tag: String, message: String){
        synchronizer.doSynced { rawLog(message, ConsoleUtils.ANSI_CYAN+"I ", "$tag -> ") }
    }
    fun w(tag: String, message: String){
        synchronizer.doSynced { rawLog(message, ConsoleUtils.ANSI_YELLOW+"W ", "$tag -> ") }
    }
    fun e(tag: String, message: String){
        synchronizer.doSynced { rawLog(message, ConsoleUtils.ANSI_RED+"E ", "$tag -> ") }
    }
    fun rawLog(message: String, prefix: String, suffix: String, printToConsole: Boolean = true){
        val time = System.currentTimeMillis().milliseconds
        val timestamp = "["+String.format(
            "%02d:%02d:%02d",
            time.inWholeHours%24 ,
            time.inWholeMinutes%60 ,
            time.inWholeSeconds%60
        )+"] "

        val str = prefix+timestamp+suffix+message

        if(printToConsole){
            message.split('\n').forEach {
                val formatted = prefix+timestamp+suffix+it
                val chunks = mutableListOf<String>(formatted.substring(0, min(maxColumns, formatted.length)))
                var i = maxColumns

                while(i<formatted.length){
                    chunks.add("  -  " + formatted.substring(i, min(i+maxColumns - 5, formatted.length)))
                    i+=maxColumns-5
                }

                chunks.forEach {itt ->
                    log.add(itt)
                    totalWrittenToConsole+=itt.length
                }
            }
        }
        totalWrittenToFile+=(str+"\n").length
        logFile.write(str+"\n"); logFile.flush()
    }
}