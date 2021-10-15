function del(obj) {
    obj.parentNode.parentNode.remove();
}

function showForm() {
    let form = document.getElementById("form_div");
    form.style.display = "block";
    let but = document.getElementById("add_button");
    but.style.display = "none";
}

function cancel() {
    let form = document.getElementById("form_div");
    form.style.display = "none";
    let but = document.getElementById("add_button");
    but.style.display = "block";
}

function onClickAddHotel() {
    let hn = document.querySelector('form input[name="hotel-name"]').value;
    let city_gz = document.getElementById("gz");
    let city_sz = document.getElementById("sz");
    let city = null;
    if (city_gz.checked) city = "GUANG ZHOU";
    else if (city_sz.checked) city = "SHEN ZHEN";
    let district = document.getElementById("district").value;
    let check_in_time = document.getElementById("check-in").value;
    let date = document.getElementById("Date").value;
    let price = document.querySelector('form input[name="price"]').value;
    let room_type = document.getElementById("room type").value;
    if (validateInput(hn, city, district, price, room_type)) {
        addRow(hn, city, district, check_in_time, date, price, room_type);
    }
}

function validateInput(hn, city, district, price, room_type) {
    if (city === null) return false;
    if (district === null) return false;
    if (hn === null) return false;
    let reg_eng = new RegExp(/^[a-zA-Z]+$/);
    if (!reg_eng.test(hn)) {
        alert("invalid hotel name");
        return false;
    }
    let reg_price = new RegExp(/^[1-9][0-9]*$/);
    if (!reg_price.test(price)) {
        alert("invalid price");
        return false;
    }
    let body = document.getElementById("tbody");
    let n = body.rows.length;
    for (i = 1; i < n; i++) {
        let nowHN = body.rows[i].cells[0].innerHTML;
        let nowCity = body.rows[i].cells[1].innerHTML;
        let nowDistrict = body.rows[i].cells[2].innerHTML;
        let nowPrice = body.rows[i].cells[5].innerHTML;
        let nowRoom = body.rows[i].cells[6].innerHTML;
        if (nowHN === hn && nowCity === city && nowDistrict === district) {
            if (nowRoom === room_type) {
                alert("Conflict type 1");
                return false;
            } else if (nowPrice === price) {
                alert("Conflict type 2");
                return false;
            }
        }
    }
    return true;
}

function initial() {
    let year = document.getElementById("district");
    year.innerHTML = "";
    year.options.add(new Option("--", null));
}

function clickCity() {
    let dist = document.getElementById("district");
    dist.options.length = 0;
    dist.options.add(new Option("--", null));
    let gz = document.getElementById("gz");
    let sz = document.getElementById("sz");
    if (gz.checked) {
        dist.options.add(new Option("TIAN HE", "TIAN HE"));
        dist.options.add(new Option("YUE XIU", "YUE XIU"));
        dist.options.add(new Option("LI WAN", "LI WAN"));
        dist.options.add(new Option("HAI ZHU", "HAI ZHU"));
        dist.options.add(new Option("BAI YUN", "BAI YUN"));
        dist.options.add(new Option("HUANG PU", "HUANG PU"));
        dist.options.add(new Option("FAN YU", "FAN YU"));
        dist.options.add(new Option("HUA DU", "HUA DU"));
        dist.options.add(new Option("NAN SHA", "NAN SHA"));
        dist.options.add(new Option("CONG HUA", "CONG HUA"));
        dist.options.add(new Option("ZENG CHENG", "ZENG CHENG"));
    } else if (sz.checked) {
        dist.options.add(new Option("NAN SHAN", "NAN SHAN"));
        dist.options.add(new Option("LUO HU", "LUO HU"));
        dist.options.add(new Option("FU TIAN", "FU TIAN"));
        dist.options.add(new Option("LONG GANG", "LONG GANG"));
        dist.options.add(new Option("PING SHAN", "PING SHAN"));
        dist.options.add(new Option("LONG HUA", "LONG HUA"));
        dist.options.add(new Option("GUANG MING", "GUANG MING"));
        dist.options.add(new Option("YAN TIAN", "YAN TIAN"));
    }
}

function getNowDate() {
    let date = new Date();
    date.setTime(date.getTime() + 24 * 3600 * 1000);
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    if (month < 10) month = '0' + month.toString();
    if (day < 10) day = '0' + day.toString();
    return year + '-' + month + '-' + day;
}

function addRow(hn, city, district, check_in_time, date, price, room_type) {
    let bodyObj = document.getElementById("tbody");
    if (!bodyObj) {
        alert("Body of Table not Exist!");
        return;
    }
    let rowCount = bodyObj.rows.length;
    let cellCount = bodyObj.rows[0].cells.length;
    bodyObj.style.display = ""; // display the tbody
    let newRow = bodyObj.insertRow(rowCount++);
    newRow.insertCell(0).innerHTML = hn;
    newRow.insertCell(1).innerHTML = city;
    newRow.insertCell(2).innerHTML = district;
    newRow.insertCell(3).innerHTML = date;
    newRow.insertCell(4).innerHTML = check_in_time;
    newRow.insertCell(5).innerHTML = price;
    newRow.insertCell(6).innerHTML = room_type;
    newRow.insertCell(7).innerHTML = bodyObj.rows[0].cells[cellCount - 1].innerHTML; // copy the "delete" button
    bodyObj.rows[0].style.display = "none"; // hide first row
}

