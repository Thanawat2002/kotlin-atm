package com.example.kotlinatm.controller

import com.example.kotlinatm.entity.Account
import com.example.kotlinatm.entity.TransactionRequest
import com.example.kotlinatm.service.AtmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AtmController {
    @Autowired
    lateinit var AtmService: AtmService

    @PostMapping("/createAccount")
    fun createAccount(@RequestBody account: Account): ResponseEntity<Map<String, Any>> {
        val response = AtmService.createAccount(account)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/getAccount/{accountNo}")
    fun getAccountNo(@PathVariable("accountNo") accountNo: String): ResponseEntity<Map<String, Any>> {
        val response = AtmService.getAccountNo(accountNo)
        return ResponseEntity.ok().body(response)
    }

    @PutMapping("/depositAccount")
    fun depositAccount(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<Map<String, Any>> {
        val response = AtmService.depositAccount(transactionRequest)
        return ResponseEntity.ok().body(response)
    }

    @PutMapping("/withdrawalAccount")
    fun withdrawalAccount(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<Map<String, Any>> {
        val response = AtmService.withdrawAccount(transactionRequest)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/account/{accountNo}")
    fun accountNo(@PathVariable("accountNo") accountNo: String): ResponseEntity<Map<String, Any>> {
        val response = AtmService.accountNo(accountNo)
        return ResponseEntity.ok().body(response)
    }

    @PostMapping("/delAccount/{accountNo}")
    fun delAccount(@PathVariable("accountNo") accountNo: String): ResponseEntity<Map<String, Any>> {
        val response = AtmService.delAccount(accountNo)
        return ResponseEntity.ok().body(response)
    }
}