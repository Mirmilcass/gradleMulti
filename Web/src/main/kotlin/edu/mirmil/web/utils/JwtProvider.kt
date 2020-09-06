package edu.mirmil.web.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class JwtProvider {
    companion object {
        //공통 key값
        @JvmField
        var tokenKey: String = String()

        //공통 시간
        @JvmField
        var tokenTime: Long = 0

        //토큰생성
        fun jwtCreator(idx: String, name: String): String {

            //토큰 생성시간
            val createDate = LocalDateTime.now()

            //토큰 마감시간
            val endDate = createDate.plusSeconds(tokenTime)

            //토큰 내용
            val claims: Map<String, String> = mapOf(
                "endTime" to endDate.format(DateTimeFormatter.ISO_DATE_TIME),
                "createdDate" to createDate.format(DateTimeFormatter.ISO_DATE_TIME),
                "idx" to idx,
                "name" to name
            )

            //토큰생성
            return Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenKey.toByteArray())
                .compact()
        }

        //토큰 값 확인
        fun jwtCheck(token: String): Claims? {
            val result = Jwts.parser().setSigningKey(tokenKey.toByteArray()).parseClaimsJws(token).body

            // 만료 예정 시간
            val endTime = LocalDateTime.parse(result["endTime"].toString())

            // 지금 시간
            val exp = LocalDateTime.now()

            // 지금 시간 보다 만료 예전 시간이 뒤라면
            // isBefore 앞보다 뒤가 전이라면
            // isAfter 앞보다 뒤가 후 라면
            //ToDo 예외처리 수정필요
            if (exp.isAfter(endTime)) throw Exception("나가")
            return result
        }
    }

    @Value("\${crypt.key}")
    fun setTokenKey(key: String) {
        tokenKey = key
    }

    @Value("\${token.time}")
    fun setTokenTime(time: Long) {
        tokenTime = time
    }
}