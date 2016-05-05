package com.jaitlapps.differ

import org.wasabi.app.AppServer
import org.wasabi.interceptors.serveStaticFilesFromFolder
import java.io.File

fun main(args : Array<String>) {
    var server = AppServer()
    server.serveStaticFilesFromFolder("WebData${File.separatorChar}public")

    server.get("/", { response.send("Hello World!") })

    server.start()
}