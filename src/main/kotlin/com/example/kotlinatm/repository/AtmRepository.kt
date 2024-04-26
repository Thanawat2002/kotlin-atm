package com.example.kotlinatm.repository

import com.example.kotlinatm.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AtmRepository: JpaRepository<Account, Long> {

    fun findByAccountNo(accountNo: String?): Account

    fun findByUsernameAndPasswordEquals(username: String?, password: String?): Account
}