# Spring Reactive Web 맛보기

### 1. 의존성
- Spring Boot DevTools
- LomBok
- Spring Reactive Web
- java 11


# 
 이걸 보기전에 reactor보고 오자
 [reactor](https://github.com/LEEJAECHEOL/reactor)
 
# 
 - Flux : N개 이상의 데이터를응답할 때 사용
 - Mono : 0 ~ 1개의 데이터를 응답할 때 사용
 - 반응형 리액터 : 프로세스가 필요함 ( Sinks.Many sink = Sinks.many().multicast().onBackpressureBuffer(); 사용)
 - 1. multicast() : 새로 들어온 데이터만 응답받음(새로 푸시 된 데이터 만 구독자에게 전송하여 배압을 준수하는 싱크 ) Hot(시퀀스 = 스트림)
	- 2. replay() : 기존 데이터 + 새로운 데이터 응답(푸시 된 데이터의 지정된 기록 크기를 새 구독자에게 재생 한 다음 새 데이터를 계속해서 실시간으로 푸시하는 싱크) cold시퀀스
	- 3. many().unicast(): 위와 동일하며 첫 번째 구독자 레지스터가 버퍼링되기 전에 푸시 된 데이터가 왜곡됩니다.
	- 4. one(): 구독자에게 단일 요소를 재생하는 싱크
	- 5. empty(): 가입자에게만 터미널 신호를 재생하지만 (오류 또는 완료) 여전히 Mono<T>(일반 유형에주의) 로 볼 수있는 싱크 <T>.


### 2. HTML 코드
 
 ```html
 <!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <style>
      .container {
        width: 80%;
        margin: 0 auto;
        border: 1px solid black;
      }
    </style>
  </head>
  <body>
    <div>알림 : <span id="notify">1</span></div>
    <div class="container">
      <div id="items"></div>
    </div>
    <script>
      fetch("http://localhost:8080/")
        .then((res) => res.json())
        .then((res) => {
          console.log(res);
          let items_el = document.querySelector("#items");
          for (i of res) {
            let item_el = document.createElement("div");
            item_el.textContent = `게시글 ${i}`;
            items_el.appendChild(item_el);
          }
        });

      const eventSource = new EventSource(`http://localhost:8080/sse`);
      eventSource.onmessage = (event) => {
        console.log(event.data);
        let notify_el = document.querySelector("#notify");
        notify_el.textContent = Number(notify_el.textContent) + 1;
      };
      eventSource.onerror = (error) => {
        eventSource.close();
      };
    </script>
  </body>
</html>
 
 ```
