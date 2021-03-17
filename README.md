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