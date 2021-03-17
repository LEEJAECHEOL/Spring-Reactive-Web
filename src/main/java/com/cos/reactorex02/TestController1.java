package com.cos.reactorex02;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Netty 서버는 비동기 서버, Tomcat 서버는 스레드 서버
// Flux는 N개 이상의 데이터를응답할 때 사용
// Mono는 0 ~ 1개의 데이터를 응답할 때 사용

@RestController
public class TestController1 {
	
	@GetMapping("flux1")
	public Flux<Integer> flux1() {
		return Flux.just(1,2,3,4).log(); //request(unbounded)
	}
	
	@GetMapping(value = "flux2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // produces MimeType을 정할수 있음
	public Flux<Integer> flux2() {
		return Flux.just(1,2,3,4).delayElements(Duration.ofSeconds(1)).log(); //request(unbounded)
	}
	
	@GetMapping(value = "flux3", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // produces MimeType을 정할수 있음
	public Flux<Long> flux3() {
		return Flux.interval(Duration.ofSeconds(1)).log();  // 1초마다 무한 응답  쓸일없음. 공부할 때만 사용
	}
	
	@GetMapping(value = "mono1") // produces MimeType을 정할수 있음
	public Mono<Integer> mono1() {
		return Mono.just(1).log(); //request(unbounded)
	}
	
}
