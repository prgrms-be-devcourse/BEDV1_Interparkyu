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

console.log("performanceId : " + performancId);
console.log("roundNumber : " + roundNumber);
console.log("date : " + date);
console.log("apiUri: " + apiUri);

fetch("http://" + apiUri)
.then(response => response.json())
.then(function(data) {
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


})