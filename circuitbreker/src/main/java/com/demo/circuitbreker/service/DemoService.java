package com.demo.circuitbreker.service;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class DemoService {
    int retryCount = 0;
    int circuitCount = 0;
    //-------------------------------------------------
    // Retry Example
    //-------------------------------------------------
    @Retry(name="demoRetry", fallbackMethod="retryFallback")
    public String retryDemo() {
        retryCount++;
        System.out.println("Retry Attempt : " + retryCount);
        throw new RuntimeException("Service Down");
    }
    public String retryFallback(Exception ex) {
        return "Retry Failed. Fallback Executed.";
    }

    //-------------------------------------------------
    // Circuit Breaker Example
    //-------------------------------------------------

    @CircuitBreaker(name="demoCB", fallbackMethod="circuitFallback")
    public String circuitDemo() {
        circuitCount++;
        System.out.println("Circuit Request : " + circuitCount);
        throw new RuntimeException("Service Down");
    }
    public String circuitFallback(Exception ex) {
        return "Circuit Breaker Open. Fallback Response.";
    }

    //-------------------------------------------------
    // Timeout Example
    //-------------------------------------------------
    @TimeLimiter(name="demoTimeout", fallbackMethod="timeoutFallback")
    public CompletableFuture<String> timeoutDemo() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            }
            catch(Exception e) {}
            return "Service Running";
        });
    }

    public CompletableFuture<String> timeoutFallback(Exception ex) {
        return CompletableFuture.completedFuture(
                "Request Timed Out."
        );
    }
}