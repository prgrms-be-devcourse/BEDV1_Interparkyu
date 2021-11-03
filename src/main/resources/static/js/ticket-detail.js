function createNode(element) {
  return document.createElement(element);
}

function append(parent, element) {
  return parent.appendChild(element);
}

const url = location.href;

const roundSeatId = url.split("roundSeatId=")[1];
const userId = 1; // 임시로 서정
let ticketId = '';

const infoDiv = document.getElementById("ticket-info");

// 티켓 정보 가져오기
fetch("http://localhost:8080/v1/tickets", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({
    "roundSeatId": roundSeatId,
    "userId": userId
  }),
})
.then(response => response.json())
.then(function (resp) {
  ticketId = resp.data.ticketId;

  fetch("http://localhost:8080/v1/tickets/" + ticketId)
  .then(response => response.json())
  .then(function (data) {

    console.log(data.data);
    // 티켓 정보 보여주기
    let thanks = createNode("h2");
    thanks.innerText = "주문이 성공적으로 접수되었습니다.";
    append(infoDiv, thanks);

    let div = createNode("div");
    div.innerText = "티켓번호: " + ticketId;
    div.innerText += "\n섹션: " + data.data.section;
    div.innerText += "\n좌석번호: " + data.data.sectionSeatNumber;
    div.innerText += "\n공연장: " + data.data.hallNames;
    div.innerText += "\n공연 회차: " + data.data.round;
    div.innerText += "\n상연 날짜: " + data.data.date;
    div.innerText += "\n시작시간: " + data.data.startTime;
    div.innerText += "\n가격: " + data.data.price;

    append(infoDiv, div);

    let home = document.getElementById("home-button")
    home.addEventListener('click', () => {
      location.replace(
          location.href.split("ticket-detail.html?")[0]
          + "index.html"
      );
    })
  })
})