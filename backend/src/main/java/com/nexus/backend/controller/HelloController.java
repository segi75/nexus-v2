// [수정] PM님의 실제 폴더 구조에 맞춘 패키지명
package com.nexus.backend.controller; 

import com.nexus.backend.common.dto.ApiResponse;
// [수정] 아까 XML에서 확인된 VO 경로 반영
import com.nexus.backend.domain.hello.HelloVO; 
// [가정] Service도 같은 도메인 패키지 안에 있다고 가정 (경로가 다르면 수정 필요)
import com.nexus.backend.service.HelloService; 
// 혹은 import com.nexus.backend.domain.hello.service.HelloService; 

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public ApiResponse<List<HelloVO>> hello() {
        log.info("=== NEXUS 2.0: Request /api/hello ===");
        
        List<HelloVO> list = helloService.getHelloList();
        
        return ApiResponse.success(list);
    }
    
	/*
	 * @GetMapping("/hello/error") public ApiResponse<Object> errorTest() {
	 * log.info("=== 에러 테스트 요청 ===");
	 * 
	 * // 강제로 예외 발생 (0으로 나누기) int result = 10 / 0;
	 * 
	 * return ApiResponse.success(null); }
	 */
}