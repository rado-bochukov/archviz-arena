function fetchData() {
    let countryName = document.getElementById('countryName').textContent; // Get the country name
    let flag = document.getElementById('flag');

    let link = 'https://restcountries.com/v3.1/name/' + countryName + '?fullText=true';

    fetch(link)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.length > 0) {
                flag.src = data[0].flags.png; // Set the src attribute of the image element
                flag.alt = countryName; // Set the alt attribute
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

fetchData();