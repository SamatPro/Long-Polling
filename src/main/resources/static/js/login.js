function signin(form) {
    $.ajax({
        url: '/login',
        type: 'POST',
        data: {
            'login': form.login.value,
            'password': form.password.value
        },
        success: function (data) {
            alert("hello");
            alert(data);
            /*let content = data[0];
            let contentTableDiv = document.getElementById('aqq');
            contentTableDiv.innerHTML = content;*/
            // localStorage.setItem ("token", JSON.stringify("value"));
        },
        error: function() {
            alert("ERROR")
        }
    })
}