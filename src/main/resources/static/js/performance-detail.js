
function createNode(element) {
    return document.createElement(element);
}

function append(parent, element) {
    return parent.appendChild(element);
}

const urlParams = new URLSearchParams(window.location.search);
const performanceUrl = urlParams.get("url");

console.log(performanceUrl);

const mainDiv = document.getElementById("main");

fetch("http://" + performanceUrl)
    .then(response => response.json())
    .then(function(data) {
        let performance = data["data"][0];
        let div = createNode("div");
        div.innerText = performance.title + " ";
        div.innerText += performance.category + " ";
        div.innerText += performance.runtime + " ";

        append(mainDiv, div);

        return performance["roundInfo"];
    })
    .then(data => console.log(data));