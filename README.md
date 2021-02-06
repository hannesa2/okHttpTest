# okHttpTest

It's just to test connectTimout behavior

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
