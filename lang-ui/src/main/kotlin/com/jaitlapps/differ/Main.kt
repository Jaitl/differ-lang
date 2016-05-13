package com.jaitlapps.differ

import com.jaitlapps.differ.services.DataService
import com.jaitlapps.differ.services.TextService
import org.wasabi.app.AppConfiguration
import org.wasabi.app.AppServer
import org.wasabi.interceptors.serveStaticFilesFromFolder
import java.io.File

fun main(args : Array<String>) {
    var server = AppServer(AppConfiguration(port = 8080))
    server.serveStaticFilesFromFolder("WebData${File.separatorChar}public")

    server.get("/", { response.redirect("index.html") })
    server.get("/api/bnf", {response.send(DataService.getBnf())})
    server.get("/api/program", {response.send(TextService.textToHtml(DataService.getProgram()))})
    server.post("/compile", {println(request.rawHeaders["code"])})

    server.start()
}