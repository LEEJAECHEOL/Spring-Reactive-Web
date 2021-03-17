package com.cos.reactorex02;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;


@CrossOrigin
@RestController
public class TestController2 {
	
//	EmitterProcessor<Integer> emmitterProcessor;
	Sinks.Many<String> sink;
	
	// multicast() : 새로 들어온 데이터만 응답받음(새로 푸시 된 데이터 만 구독자에게 전송하여 배압을 준수하는 싱크 ) Hot(시퀀스 = 스트림)
	// replay() : 기존 데이터 + 새로운 데이터 응답(푸시 된 데이터의 지정된 기록 크기를 새 구독자에게 재생 한 다음 새 데이터를 계속해서 실시간으로 푸시하는 싱크) cold시퀀스
	
	public TestController2() {
		this.sink = Sinks.many().multicast().onBackpressureBuffer();
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Integer> findAll(){
		return Flux.just(1,2,3,4,5,6).log();
	}
	
	
	@GetMapping("/send")
	public void send() {
		sink.tryEmitNext("Hello World");
	}
	
	// data : 실제값 \n\n
//	@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<String> sse() { // Flux<ServerSentEvent<String>>
//		return sink.asFlux().map(e -> ServerSentEvent.builder(e).build());
//	}
	
	// 위의 주석과 같은 것임.
	@GetMapping(value = "/sse")
	public Flux<ServerSentEvent<String>> sse() { // ServerSendEvent의 ContentType은 text event stream
		return sink.asFlux().map(e -> ServerSentEvent.builder(e).build()).doOnCancel(()->{
			System.out.println("sse 종료됨.");
			sink.asFlux().blockLast(); // 마지막이니깐 sse 끊어!!
		}); // 구독
	}
	
}
