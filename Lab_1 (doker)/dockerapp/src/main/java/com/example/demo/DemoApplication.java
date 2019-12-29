package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.*;
import java.util.concurrent.atomic.AtomicLong;


@SpringBootApplication
@RestController
public class DemoApplication {
        AtomicLong i = new AtomicLong(0);
        Jedis jedis = new Jedis("redis");

        @RequestMapping("/")
        public String home() {
            return "Hello Docker World. This is second version";
        }

        public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);

            Jedis jedis = new Jedis("redis");

            jedis.del("counter");
            jedis.set("counter", "0");
        }

        @RequestMapping("/count")
        public String count() {


            String buff = i.incrementAndGet() + " | " + jedis.incr("counter" + "\r");
            return buff;
        }

        @RequestMapping("/stat")
        public String stat() {
            String buff = i + " | " + jedis.incr("counter" + "\r");
            return buff;
        }

}