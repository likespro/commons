package eth.likespro.commons.numeric

import org.apache.commons.codec.binary.Hex
import java.math.BigDecimal
import java.math.RoundingMode

class NumericUtils {
    companion object{
        fun Double.round(places: Int): Double{
            require(places >= 0)
            var bd = BigDecimal.valueOf(this)
            bd = bd.setScale(places, RoundingMode.HALF_UP)
            return bd.toDouble()
        }
        fun Int.toReadableInformationSize(places: Int = 1): String{
            return if(this >= 1024*1024*1024) (this/1024.0/1024.0/1024.0).round(places).toString()+" GiB"
            else if(this >= 1024*1024) (this/1024.0/1024.0).round(places).toString()+" MiB"
            else if(this >= 1024) (this/1024.0).round(1).toString()+" KiB"
            else "$this B"
        }
        fun Long.toReadableInformationSize(places: Int = 1): String{
            return if(this >= 1024*1024*1024) (this/1024.0/1024.0/1024.0).round(places).toString()+" GiB"
            else if(this >= 1024*1024) (this/1024.0/1024.0).round(places).toString()+" MiB"
            else if(this >= 1024) (this/1024.0).round(1).toString()+" KiB"
            else "$this B"
        }
        fun String.hexToByteArray(): ByteArray{
            return Hex.decodeHex(this)
        }
        fun ByteArray.toHexString(): String{
            return Hex.encodeHexString(this)
        }
    }
}