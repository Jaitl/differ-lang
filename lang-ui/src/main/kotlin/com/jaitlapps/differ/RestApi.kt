package com.jaitlapps.differ

import com.jaitlapps.differ.models.DiffProgram
import com.jaitlapps.differ.services.DataService
import com.jaitlapps.differ.services.TextService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestApi {

    @RequestMapping("/bnf")
    fun getBnf(): String {
        return DataService.getBnf();
    }

    @RequestMapping("/program", produces = arrayOf("text/html;charset=UTF-8"))
    fun getProgram(): String {
        return TextService.textToHtml(DataService.getProgram())
    }

    @RequestMapping(value = "/compile", method = arrayOf(RequestMethod.POST))
    fun compile(@RequestBody program: DiffProgram) {
        println(program.code)
    }
}