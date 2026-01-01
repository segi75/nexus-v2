package com.nexus.backend.service;

import com.nexus.backend.domain.hello.HelloMapper;
import com.nexus.backend.domain.hello.HelloVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HelloService {

    private final HelloMapper helloMapper;

    public List<HelloVO> getHelloList() {
        return helloMapper.selectHelloList();
    }
}