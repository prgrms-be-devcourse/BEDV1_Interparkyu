function createNode(element) {
    return document.createElement(element);
}

function append(parent, element) {
    return parent.appendChild(element);
}

const mainDiv = document.getElementById("main");

fetch("http://localhost:8080/v1/performances")
    .then(response => response.json())
    .then(function (data) {
        let performances = data["data"];
        return performances.map(function (performance) {
            let div = createNode("div");
            console.log(performance.title);
            div.innerText = performance.title + " ";
            div.innerText += performance.category + " ";
            div.innerText += performance.hallName + " ";

            let button = createNode("a");
            let linkText = document.createTextNode("자세히")
            button.appendChild(linkText);
            button.title = "자세히";
            button.href = "performance-detail.html?url=" + performance.url;
            append(div, button);
            append(mainDiv, div);
        })
    })
    .catch(error => console.log('error', error));
