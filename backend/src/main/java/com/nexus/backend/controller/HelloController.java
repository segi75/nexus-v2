package com.nexus.backend.controller;

import com.nexus.backend.domain.hello.HelloVO;
import com.nexus.backend.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
// 로컬 개발 환경(Nuxt 포트)에서의 접근 허용
@CrossOrigin(origins = "http://localhost:3000") 
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public ResponseEntity<List<HelloVO>> getHello() {
        List<HelloVO> list = helloService.getHelloList();
        return ResponseEntity.ok(list);
    }
}