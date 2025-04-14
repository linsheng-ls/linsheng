package com.example.awswebdemo.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.logging.*;

@RestController
public class LogController {

    private static final Logger logger = Logger.getLogger(LogController.class.getName());

    @PostMapping("/log")
    public void receiveLog(@RequestBody Map<String, String> payload) {
        String log = payload.get("log");
        logger.info("Front-end Log: " + log);



    }
}
