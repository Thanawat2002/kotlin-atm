package com.example.kotlinatm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinAtmApplication

fun main(args: Array<String>) {
	runApplication<KotlinAtmApplication>(*args)
}
