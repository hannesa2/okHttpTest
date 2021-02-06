package info.hannes.okhttp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Duration

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ResponseTimeTest {
    private val appContext = ApplicationProvider.getApplicationContext<Context>()

    private var client = OkHttpClient.Builder()
        .connectTimeout(Duration.ofSeconds(4))
        .writeTimeout(Duration.ofSeconds(7))
        .readTimeout(Duration.ofSeconds(7))
        .callTimeout(Duration.ofSeconds(10))
        .retryOnConnectionFailure(false)
        .build()

    @Test
    fun testInterntWithoutService() {
        connectTest("http://1.1.1.1", "I")
    }

    @Test
    fun testAllIP4AInterfaces() {
        val list = NetworkUtils.getLocalIpAddress()

        list.sorted().forEach {
            connectTest("http://$it", "A")
        }

        list.sortedDescending().forEach {
            connectTest("http://$it", "D")
        }
    }

    private fun connectTest(server: String, preFix: String) {
        val start = System.currentTimeMillis()
        val request: Request = Request.Builder()
            .url(server) // non routable address
            .build()
        catchThrowable { client.newCall(request).execute() }
        val end = System.currentTimeMillis()
        val responseTime: Long = end - start
        println("responseTime$preFix $server $responseTime")
    }

}
