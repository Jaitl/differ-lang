package com.jaitlapps.differ

import org.wasabi.app.AppConfiguration
import org.wasabi.app.AppServer
import org.wasabi.interceptors.serveStaticFilesFromFolder
import java.io.File

fun main(args : Array<String>) {
    var server = AppServer(AppConfiguration(port = 8080))
    server.serveStaticFilesFromFolder("WebData${File.separatorChar}public")

    server.get("/", { response.redirect("index.html") })

    server.start()
}