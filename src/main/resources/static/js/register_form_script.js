function enableCreateUser() {
    var occupation = document.getElementById("occupation").value;
    if (occupation === "ARTIST") {
        document.getElementById("creator_type").removeAttribute('disabled');
        document.getElementById("price_per_image").removeAttribute('disabled');
    } else {
        document.getElementById("creator_type").setAttribute('disabled', 'disabled');
        document.getElementById("price_per_image").setAttribute('disabled', 'disabled');
        document.getElementById("error_price").className = "";
        document.getElementById("error_price").innerHTML = "";
        document.getElementById("creator_error").className = "";
        document.getElementById("creator_error").innerHTML = "";
        document.getElementById("creator_type").value = "";
        document.getElementById("price_per_image").value = "";
    }
}

function setErrorPrice() {
    var occupation = document.getElementById("occupation").value;
    var price_per_image = document.getElementById("price_per_image").value;

    if (occupation === 'ARTIST' && price_per_image === "") {
        document.getElementById("error_price").className = "errors alert alert-danger";
        document.getElementById("error_price").innerHTML = "Suggested price is required.";
    } else {
        document.getElementById("error_price").className = "";
        document.getElementById("error_price").innerHTML = "";
    }
}

function setErrorCreator() {
    var occupation = document.getElementById("occupation").value;
    var creator_type = document.getElementById("creator_type").value;

    if (occupation === 'ARTIST' && creator_type === "") {
        document.getElementById("creator_error").className = "errors alert alert-danger";
        document.getElementById("creator_error").innerHTML = "Creator type should be selected!";
    } else {
        document.getElementById("creator_error").className = "";
        document.getElementById("creator_error").innerHTML = "";

    }
}
function setRegisterButton() {
    setErrorCreator()
    setErrorPrice()
    var register_button = document.getElementById("register_button")
    var occupation = document.getElementById("occupation").value;
    var creator_type = document.getElementById("creator_type").value;
    var price_per_image = document.getElementById("price_per_image").value;
    if (occupation === 'ARTIST' && creator_type === "" && price_per_image === "") {
        register_button.setAttribute('disabled', 'disabled')
    } else if (occupation === 'ARTIST' && price_per_image === "") {
        register_button.setAttribute('disabled', 'disabled')
    } else if (occupation === 'ARTIST' && creator_type === "") {
        register_button.setAttribute('disabled', 'disabled')
    } else register_button.removeAttribute('disabled');
}

setRegisterButton()
enableCreateUser()
setErrorPrice()
setErrorCreator()