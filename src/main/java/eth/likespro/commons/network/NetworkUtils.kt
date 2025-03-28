package eth.likespro.commons.network

import org.minidns.hla.ResolverApi
import java.io.IOException
import java.net.URL

class NetworkUtils {
    companion object{
        fun URL.getSRVRecordAddress(): String? {
            val srv = this.readText()
            try {
                val result = ResolverApi.INSTANCE.resolveSrv(srv)
                if (result.wasSuccessful()) {
                    val srvRecords = result.sortedSrvResolvedAddresses
                    for (record in srvRecords) {
                        return record.srv.target.toString()+":"+record.srv.port.toString()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return null
        }
    }
}