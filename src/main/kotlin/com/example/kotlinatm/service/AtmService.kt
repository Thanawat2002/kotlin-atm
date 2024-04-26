package com.example.kotlinatm.service

import com.example.kotlinatm.entity.Account
import com.example.kotlinatm.entity.TransactionRequest
import com.example.kotlinatm.repository.AtmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.log
import kotlin.random.Random

@Service
class AtmService {
    @Autowired
    lateinit var atmRepository: AtmRepository

    fun account(): List<Account> {
        try {
            return atmRepository.findAll()
        } catch (ex: Exception) {
            return emptyList()
        }
    }

    fun createAccount(newAccount: Account): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        var msg = ""
//        newAccount.gender = newAccount.gender?.trim()
//        newAccount.firstName = newAccount.firstName?.trim()
//        newAccount.lastName = newAccount.lastName?.trim()
//        newAccount.tel = newAccount.tel?.trim()

        try {
            val checkPhone = newAccount.tel?.toInt()
            try {

                if (newAccount.age!! >= 10 && newAccount.amount!! >= BigDecimal.valueOf(1000)) {
                    val randomValues = Random.nextLong(1000000000, 9999999999L)
                    newAccount.accountNo = randomValues.toString();
                    response["status"] = true
                    response["message"] = "สร้างบัญชีสำเร็จ"
                    response["accountNo"] = newAccount.accountNo!!
                    response["firstName"] = newAccount.firstName!!
                    response["lastName"] = newAccount.lastName!!
                    newAccount.amount = newAccount.amount!!.setScale(2, RoundingMode.DOWN)
                    atmRepository.save(newAccount);
                } else if (newAccount.age!! > 10) {
                    if (newAccount.amount!! >= BigDecimal.valueOf(1000)) {
                        val randomValues = Random.nextLong(1000000000, 9999999999L)
                        newAccount.accountNo = randomValues.toString();
                        response["status"] = true
                        response["message"] = "สร้างบัญชีสำเร็จ"
                        response["accountNo"] = newAccount.accountNo!!
                        response["firstName"] = newAccount.firstName!!
                        response["lastName"] = newAccount.lastName!!
                        atmRepository.save(newAccount);
                    } else {
                        msg = "ฝากเงินขั้นต่ำ 1000 บาท"
                        response["status"] = false
                        response["message"] = msg
                    }
                } else if (newAccount.amount!! >= BigDecimal.valueOf(1000)) {
                    if (newAccount.age!! > 10) {
                        val randomValues = Random.nextLong(1000000000, 9999999999L)
                        newAccount.accountNo = randomValues.toString();
                        response["status"] = true
                        response["message"] = "สร้างบัญชีสำเร็จ"
                        response["accountNo"] = newAccount.accountNo!!
                        response["firstName"] = newAccount.firstName!!
                        response["lastName"] = newAccount.lastName!!
                        atmRepository.save(newAccount);
                    } else {
                        msg = "อายุมากกว่า 10 ปี ขึ้นไป"
                        response["status"] = false
                        response["message"] = msg
                    }
                } else {
                    msg = "ต้องอายุมากกว่า 10 ปี ขึ้นไป และ ฝากเงินขั้นต่ำ 1000 บาท"
                    response["status"] = false
                    response["message"] = msg
                }
            } catch (ex: Exception) {
                response["status"] = false
                response["message"] = "โปรดกรอกข้อมูลให้ครบ: ${ex.message}"
            }
        } catch (ex: Exception) {
            response["status"] = false
            response["message"] = "เบอร์โทรไม่ถูกต้อง"
        }

        return response
    }

    fun loginAccount(account: Account): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val data = atmRepository.findByUsernameAndPasswordEquals(account.username, account.password)
            response["message"] = "เข้าสู่ระบบสำเร็จ"
            response["status"] = true
            response["data"] = data
        } catch (ex: Exception) {
            response["message"] = "ไม่พบบัญชี"
            response["status"] = false
        }
        return response
    }

    fun getAccountNo(accountNo: String): Map<String, Any>? {
        val response: MutableMap<String, Any> = mutableMapOf()

        try {
            val trimmedAccountNo = accountNo.trim()
            val optionalAccount = atmRepository.findByAccountNo(trimmedAccountNo)
            val dec = DecimalFormat("###,###,###.00")
            val formatAmt = dec.format(optionalAccount.amount)
            val phoneNumber = optionalAccount.tel
            val formattedPhoneNumber = phoneNumber!!.replaceFirst("(\\d{3})(\\d{3})(\\d{4})".toRegex(), "$1-$2-$3")
            optionalAccount.tel = formattedPhoneNumber

            response["status"] = true
            response["password"] = optionalAccount.password.toString()
            response["tel"] = optionalAccount.tel.toString()
            response["address"] = optionalAccount.address.toString()
            response["firstName"] = optionalAccount.firstName.toString()
            response["lastName"] = optionalAccount.lastName.toString()
            response["birthDate"] = optionalAccount.birthDate.toString()
            response["status"] = optionalAccount.status.toString()
            response["religion"] = optionalAccount.religion.toString()
            response["favoriteColor"] = optionalAccount.favoriteColor.toString()
            response["email"] = optionalAccount.email.toString()

        } catch (ex: Exception) {
            response["message"] = "ไม่พบเลขบัญชี"
        }

        return response
    }

    fun updateAccount(accountNo: String, account: Account): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val optionalAccount = atmRepository.findByAccountNo(accountNo)
            if (optionalAccount.accountNo != null) {
                optionalAccount.accountNo = account.accountNo ?: optionalAccount.accountNo
                optionalAccount.username = account.username ?: optionalAccount.username
                optionalAccount.password = account.password ?: optionalAccount.password
                optionalAccount.firstName = account.firstName ?: optionalAccount.firstName
                optionalAccount.lastName = account.lastName ?: optionalAccount.lastName
                optionalAccount.tel = account.tel ?: optionalAccount.tel
                optionalAccount.address = account.address ?: optionalAccount.address
                optionalAccount.age = account.age ?: optionalAccount.age
                optionalAccount.birthDate = account.birthDate ?: optionalAccount.birthDate
                optionalAccount.status = account.status ?: optionalAccount.status
                optionalAccount.religion = account.religion ?: optionalAccount.religion
                optionalAccount.favoriteColor = account.favoriteColor ?: optionalAccount.favoriteColor
                optionalAccount.email = account.email ?: optionalAccount.email
                optionalAccount.amount = account.amount ?: optionalAccount.amount
                atmRepository.saveAndFlush(optionalAccount)

                response["message"] = "อัพเดทข้อมูลสำเร็จ"
                response["status"] = true
            } else if (optionalAccount.accountNo != accountNo) {
                response["message"] = "ไม่พบเลขบัญชี"
                response["status"] = false
            }
        } catch (ex: Exception) {
            response["message"] = "ไม่พบเลขบัญชี"
            response["status"] = false
        }
        return response
    }

    fun depositAccount(transactionRequest: TransactionRequest): Map<String, Any>? {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val trimmedAccountNo = transactionRequest.accountNo.trim()
            val trimmedAmount = transactionRequest.amount.toString().trim()
            val optionalAccount = atmRepository.findByAccountNo(trimmedAccountNo)
            val depositNew = trimmedAmount.toBigDecimal().setScale(2, RoundingMode.DOWN) + optionalAccount.amount!!
            val dec = DecimalFormat("###,###,###.00")
            val formatAmt = dec.format(depositNew)
            optionalAccount.amount = depositNew
            if (transactionRequest.amount >= 1 ) {
                response["message"] = "ฝากเงินสำเร็จ"
                response["status"] = true
                response["amount"] = "$formatAmt บาท"
                response["AccountNo"] = optionalAccount.accountNo!!
                atmRepository.save(optionalAccount)
            } else {
                response["message"] = "ฝากเงินขั้นต่ำ 1 บาท"
                response["status"] = false
            }

        } catch (ex: Exception) {
            response["message"] = "ไม่พบเลขบัญชี"
            response["status"] = false
        }
        return  response
    }

    fun withdrawAccount(transactionRequest: TransactionRequest): Map<String, Any>? {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val trimmedAccount = transactionRequest.accountNo.trim()
            val optionalAccount = atmRepository.findByAccountNo(trimmedAccount)
            val withdrawNew = optionalAccount.amount!! - transactionRequest.amount.toBigDecimal().setScale(2, RoundingMode.DOWN)
            if (optionalAccount.amount!! < transactionRequest.amount.toBigDecimal()) {
                response["status"] = false
                response["message"] = "เงินในบัญชีไม่พอ"
            } else {
                if (transactionRequest.amount < 100) {
                    response["status"] = false
                    response["message"] = "ถอนขั้นต่ำ 100 บาท"
                } else {
                    optionalAccount.amount = withdrawNew
                    atmRepository.save(optionalAccount)
                    val dec = DecimalFormat("###,###,###,###,##0.00")
                    val formatAmt = dec.format(optionalAccount.amount!!)
                    response["status"] = true
                    response["message"] = "ถอนเงินสำเร็จ"
                    response["amount"] = "$formatAmt บาท"
                }
            }
        } catch (ex: Exception) {
            response["status"] = false
            response["message"] = "ไม่พบเลขบัญชี"
        }
        return  response
    }

    fun accountNo(accountNo: String): Map<String, Any>? {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val trimmedAccount = accountNo.trim()
            val optionalAccount = atmRepository.findByAccountNo(trimmedAccount)
            val dec = DecimalFormat("###,###,###.00")
            val formatAmt = dec.format(optionalAccount.amount!!)
            response["amount"] = "$formatAmt บาท"
            response["AccountNo"] = optionalAccount.accountNo!!
        } catch (ex: Exception) {
            response["message"] = "ไม่พบเลขบัญชี"
        }
        return response
    }

    fun delAccount(accountNo: String): Map<String, Any>? {
        val response: MutableMap<String, Any> = mutableMapOf()
        try {
            val trimmedAccount = accountNo.trim()
            val optionalAccount = atmRepository.findByAccountNo(trimmedAccount)
            atmRepository.delete(optionalAccount)
            response["status"] = true;
            response["message"] = "ลบบัญชีสำเร็จ"
            response["accountNo"] = optionalAccount.accountNo!!
        } catch (ex: Exception) {
            response["status"] = false;
            response["message"] = "ไม่พบเลขบัญชี"
        }

        return response
    }
}