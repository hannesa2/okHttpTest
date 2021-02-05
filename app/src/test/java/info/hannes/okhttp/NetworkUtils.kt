package info.hannes.okhttp

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object NetworkUtils {

    fun getLocalIpAddress(): MutableList<String> {
        val result = mutableListOf<String>()
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    println("network ${inetAddress.hostAddress} ${inetAddress.isLoopbackAddress} ${true}")
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        result.add(inetAddress.hostAddress.toString())
                    }
                }
            }
        } catch (ex: SocketException) {
            println(ex.toString())
        }
        return result
    }


}