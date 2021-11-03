function createNode(element) {
  return document.createElement(element);
}

function append(parent, element) {
  return parent.appendChild(element);
}

const url = location.href;
const performancId = url.split("v1/performances/")[1].split("/")[0]
const roundNumber = url.split("v1/performances/")[1].split("/")[2];
const date = url.split("seats?date=")[1];

const apiUri = url.split("url=")[1];

const mainDiv = document.getElementById('seat-select-main'); // 좌석 버튼들을 보여주는 용도
const infoDiv = document.getElementById("info-section"); // 선택된 정보를 간략하게 보여주는 용도
const payBtn = document.getElementById("pay-button"); // 결제버튼 두는 용도

function writeSelectedRoundSeatInfo(infoDiv, rs_div_class_string) {
  // 중요한 정보는 infoDiv의 class로 넣어두고 나머지만 보여주자
  const data = rs_div_class_string.split(",")
  const roundSeatId = data[0].split(':')[1];
  const price = data[1].split(':')[1];
  const round = data[2].split(':')[1];
  const seatId = data[3].split(':')[1];
  const section = data[4].split(':')[1];
  const sectionSeatNumber = data[5].split(':')[1];

  infoDiv.innerText = "Section: " + section;
  infoDiv.innerText += "\n좌석 번호: " + sectionSeatNumber;
  infoDiv.innerText += "\n가격: " + price + " 원";

  payBtn.disabled = false;
}

function createButtons(mainDiv, roundSeats) {
  // 각 좌석들을 버튼으로 만들고, onclick=handleRoundSeatClicked() 해주기
  roundSeats.map(roundSeat => {
    // 버튼 만들기
    let rs_div = createNode("input");
    rs_div.type = 'button';

    // 이미 선택된 좌석은 비활성화
    if (roundSeat.reservationStatus !== 'NOT_RESERVED') {
      rs_div.disabled = true;
    }

    // 버튼에 정보 넣어주기 -> 표시 및 다음 페이지로 전달해 줄 데이터
    rs_div.value = roundSeat.section + '-' + roundSeat.sectionSeatNumber;
    rs_div.innerText = 'roundSeatId:' + roundSeat.id; // ,로 스플릿하고 다시 :로 스플릿해서 사용하기
    rs_div.innerText += ',price:' + roundSeat.price;
    rs_div.innerText += ',round:' + roundSeat.round;
    rs_div.innerText += ',seatId:' + roundSeat.seatId;
    rs_div.innerText += ',section:' + roundSeat.section;
    rs_div.innerText += ',sectionSeatNumber:' + roundSeat.sectionSeatNumber;
    rs_div.class = rs_div.innerText;

    // 버튼 눌렸을 때 역할 넣어주기 (수정중)
    rs_div.addEventListener('click', () => {
      writeSelectedRoundSeatInfo(infoDiv, rs_div.class);
    })

    append(mainDiv, rs_div);
  })
}

fetch("http://" + apiUri)
.then(response => response.json())
.then(function (data) {
  const dat = data.data[0];

  const title = dat.title;
  const round = dat.round;
  const date = dat.date;
  const remainingSeatsCount = dat.remainingSeatsCount;
  const startTime = dat.startTime;
  const endTime = dat.endTime;
  const ticketingStartDateTime = dat.ticketingStartDateTime;
  const ticketingEndDateTime = dat.ticketingEndDateTime;
  const ticketCancelableUntil = dat.ticketCancelableUntil;
  const hall = dat.hall;
  const sectionRemainingSeatCount = dat.sectionRemainingSeatCount;
  const roundSeats = dat.roundSeats;
  console.log(roundSeats);

  createButtons(mainDiv, roundSeats);

})