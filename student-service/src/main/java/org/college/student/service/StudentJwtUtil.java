package org.college.student.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class StudentJwtUtil {

    private static final String SECRET =
            "student-auth-secret-key-1234567890"; // >= 32 chars

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateStudentToken(String studentId) {

        return Jwts.builder()
                .claim("studentId", studentId)
                .claim("role", "STUDENT").issuedAt(new Date()).expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hour
                )
                .signWith(key)
                .compact();
    }
}
