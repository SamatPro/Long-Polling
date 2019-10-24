<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>Login</title>

</head>
<body>
    <input id="token" value="${token}" hidden>

    <script type="text/javascript">

       /* var token = document.getElementById('token').value;

        localStorage.setItem ('token', token);

        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/chat', true);

        xhr.onload = function () {
            xhr.setRequestHeader('AUTH', token);
        };

        xhr.send(null);
        window.location.replace('/chat');*/
        window.onload = function () {
            var token = document.getElementById('token').value;

            localStorage.setItem ('token', token);

            var request = new XMLHttpRequest();
            var path='/chat';

            request.open('GET', path, true);
            request.setRequestHeader('AUTH', token);
            request.send(null);
            // request.redirect("/chat");
            window.location.replace('/chat');
        }
    </script>


</body>
</html>