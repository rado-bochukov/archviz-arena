// // this is an arrow function
// const loadCountryAPI = () =>{
//     // fetch url of rest country from website
//     fetch('https://restcountries.com/v3.1/all')
//         .then(res => res.json())
//         .then(data => displayCountries(data))
// }
//
// // displaying all countries
// const displayCountries = countries =>{
//     // console.log(countries);
//     const countriesHTML = countries.map(country => getCountry());
//     // displaying div to html
//     const container = document.getElementById('countries');
//     container.innerHTML = countriesHTML.join(' ');
// }
//
// // get data and set it to html
// const getCountry = (country) =>{
//     fetch('https://restcountries.com/v3.1/name/Bulgaria?fullText=true')
//         .then(response=>response.json())
//     console.log(country)
//     let code = country.code;
//     const url="https://flagsapi.com/"
//     const urlEnd="/flat/64.png"
//     let fullLink=url+code+urlEnd;
//     return `
//
//         <img src="${fullLink}" />
//
//     `
// }
// loadCountryAPI()

let container = document.getElementById('container')
let flag = document .getElementById('flag')

function fetchData(){
    fetch('https://restcountries.com/v3.1/name/Bulgaria?fullText=true')
        .then(response=>response.json())
        .then(data=>{
            console.log(data)
            flag=data[0].flags.png
            let code = data[0].cca2


            container.innerHTML=`<img class="zname" src=${data[0].flags.png} alt="flag">`
            // container.innerHTML=`<span >Area:  </span>${data[0].area}`
        })
}
fetchData()