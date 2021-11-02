function createNode(element) {
    return document.createElement(element);
}

function append(parent, element) {
    return parent.appendChild(element);
}

function getRound(date) {
    const round = document.querySelector('input[name="round"]:checked')

    let seatsDetailDivHtml = "<div>"
    seatsDetailDivHtml += "<button onclick='getSeatsDetailInfo(" + round.value
        + ", " + date + ")'>" + "조회하기" + "</button>"
    seatsDetailDivHtml += "</div>"
    seatsDetailDiv.innerHTML = seatsDetailDivHtml
}

function getRoundsDetailInfo(date) {
    fetch("http://" + performanceUrl + "/round?date=" + date)
    .then(response => response.json())
    .then(function(data) {
        let rounds = data["data"];
        console.log(rounds)

        let roundsDetailDivHtml = "<div>"
        rounds.map(function(round) {
            roundsDetailDivHtml += "<input type='radio' name='round' value="
                + "\"" + round.round + "\" " + "onclick=getRound(" + date
                + ") + />"
                + round.round + "</input>"
            roundsDetailDivHtml += "<br></br>"
            roundsDetailDivHtml += "startTime : " + round.startTime
            roundsDetailDivHtml += "<br></br>"
            roundsDetailDivHtml += "endTime : " + round.endTime
            roundsDetailDivHtml += "<br></br>"
            roundsDetailDivHtml += "remainingSeatsCount : "
                + round.remainingSeatsCount
            roundsDetailDivHtml += "<br></br>"
            var obj = round["sectionRemainingSeatCount"][round.round]
            for (var key in obj) {
                roundsDetailDivHtml += "section : " + key
                    + "sectionRemainingSeat : " + obj[key];
                roundsDetailDivHtml += "<br></br>"
            }
        })
        roundsDetailDivHtml += "</div>"
        roundsDetailDiv.innerHTML = roundsDetailDivHtml
    })
    .then(data => console.log(data));
}

function getSeatsDetailInfo(round, date) {
    location.replace(location.href.split('performance-')[0] +
        "/seat-select.html?url=" + performanceUrl + "/round/" + round
        + "/seats?date=" + date)

}

const urlParams = new URLSearchParams(window.location.search);
const performanceUrl = urlParams.get("url");

const mainDiv = document.getElementById("main");
const calendarDiv = document.getElementById("calendar")
const roundsDetailDiv = document.getElementById("roundDetail")
const seatsDetailDiv = document.getElementById("seatsDetail")

fetch("http://" + performanceUrl)
.then(response => response.json())
.then(function(data) {
    let performance = data["data"];
    let div = createNode("div");
    div.innerText = performance.title + " ";
    div.innerText += performance.category + " ";
    div.innerText += performance.runtime + " ";
    append(mainDiv, div);

    let calendarDivHtml = "<div>"
    performance.roundDate.map(function (v) {
        console.log("date: " + v.date);
        calendarDivHtml += "<button onclick='getRoundsDetailInfo(" + v.date
            + ")'>" + v.date + "</button>"
    })

    calendarDivHtml += "</div>"
    calendarDiv.innerHTML = calendarDivHtml

    return performance["roundInfo"];
})
.then(data => console.log(data));