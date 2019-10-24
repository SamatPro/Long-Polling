function sendMessage(text) {
    let body = {
        text: text,
        token: localStorage.getItem('token')
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        headers: {
            'AUTH': localStorage.getItem('token')
        },
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        success: function () {
            $('#message').val('');
        }
    });

}
function receiveMessage() {
    $.ajax({
        url: "/messages?token=" + localStorage.getItem('token'),
        method: "GET",
        dataType: "json",
        headers: {
            'AUTH': localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (response) {
            $('#messages').first().after('<li style="list-style-type: none;"><span style="color: #4d91bf">'+ response[0]['userName']+':  </span> ' + response[0]['text'] + '</li>')
            receiveMessage();
        }
    })
}

function login() {

    let body = {
        token: localStorage.getItem('token'),
        text: '<span style="color: gray"> добавился в беседу</span>'
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        headers: {
            'AUTH': localStorage.getItem('token')
        },
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            receiveMessage();
        }
    });
}