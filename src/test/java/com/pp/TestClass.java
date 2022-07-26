package com.pp;

import com.pp.domain.AlgorithmCall;
import com.pp.service.IAlgorithmCallService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestClass {
    @Autowired
    IAlgorithmCallService service;
    @Test
    public void test(){
        List<AlgorithmCall> callList =  service.selectAlgorithmCall(10001);
        System.out.println(Arrays.toString(callList.toArray()));
    }
}
