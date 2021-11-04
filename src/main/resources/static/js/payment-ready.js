function createNode(element) {
  return document.createElement(element);
}

function append(parent, element) {
  return parent.appendChild(element);
}

const infoDiv = document.getElementById("selected-seat");

const url = location.href;
const params = url.split("html?")[1].split("&")

const roundSeatId = params[0].split("=")[1]
const price = params[1].split("=")[1]
const section = params[2].split("=")[1]
const sectionSeatNumber = params[3].split("=")[1]

let div = createNode("div");
div.innerText = "Section: " + section;
div.innerText += "\n좌석번호: " + sectionSeatNumber;
div.innerText += "\n가격: " + price + " 원";

append(infoDiv, div);

const payment_button = document.getElementById("pay-button");
payment_button.addEventListener('click', () => {
  location.replace(
      location.href.split("payment-ready.html?")[0]
      + "ticket-detail.html?roundSeatId=" + roundSeatId
  );
})