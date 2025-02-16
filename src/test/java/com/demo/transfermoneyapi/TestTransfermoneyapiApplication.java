package com.demo.transfermoneyapi;

import org.springframework.boot.SpringApplication;

public class TestTransfermoneyapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(TransfermoneyapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
