package com.example.testcode.user

import com.example.testcode.ApiTest
import com.example.testcode.user.steps.UserLoadSteps
import com.example.testcode.user.steps.UserLoginSteps
import com.example.testcode.user.steps.UserSaveSteps
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UserApiTest: ApiTest(){
    init {
        describe("유저 생성 api test") {
            context("사용자가 유저 생성 api를 호출한다.") {
                it("유저를 생성한다.") {
                    val request = UserSaveSteps.유저생성모델생성()
                    val response = UserSaveSteps.유저생성요청(request)
                    response.statusCode() shouldBe 201
                }
            }
        }

        describe("유저 로그인 api test") {
            context("사용자가 유저 로그인 api를 호출한다.") {
                it("유저 로그인 성공 - jwt 토큰을 반환") {
                    val response = UserSaveSteps.유저생성요청(UserSaveSteps.유저생성모델생성())
                    response.statusCode() shouldBe 201
                    val loginResponse = UserLoginSteps.유저로그인요청("email1", "password")
                    loginResponse.statusCode() shouldBe 200
                    loginResponse.header("Authorization") shouldNotBe null
                }

                it("유저 로그인 실패 - 401 반환") {
                    val loginResponse = UserLoginSteps.유저로그인요청("email1", "password")
                    loginResponse.statusCode() shouldBe 401
                }
            }
        }

        describe("유저 정보 조회 api test") {
            context("사용자가 유저 정보 조회 api를 호출한다.") {
                it("유저 정보 조회 성공") {
                    val response = UserLoadSteps.유저조회요청()
                    response.statusCode() shouldBe 200
                    response.body().jsonPath().getLong("id") shouldBe 1
                    response.body().jsonPath().getString("email") shouldBe "email1"
                    response.body().jsonPath().getString("name") shouldBe "name"
                }
            }
            context("사용자 정보 조회 실패") {
                it("유저 정보 조회 실패 - 401 반환") {
                    val response = UserLoadSteps.유저조회요청_401_토큰없음()
                    response.statusCode() shouldBe 401
                }
            }
        }
    }
}