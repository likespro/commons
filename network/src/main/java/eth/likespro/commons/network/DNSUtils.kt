/*
 * Copyright 2025 likespro.
 *
 * From https://github.com/likespro/commons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eth.likespro.commons.network

import eth.likespro.commons.network.HTTPUtils.get
import org.minidns.hla.ResolverApi
import java.io.IOException
import java.net.URI

object DNSUtils {
    /**
     * Resolves and retrieves the address of an SRV record from the URI.
     *
     * @return A string representation of the resolved SRV record address in the format `target:port`,
     *         or null if the resolution was unsuccessful.
     */
    fun URI.getSRVRecordAddress(): String? {
        val srv = this.get()
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