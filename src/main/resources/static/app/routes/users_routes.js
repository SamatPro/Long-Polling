bodyParser = require('body-parser').json();

// описываем фунцию для обработки post-запроса на url /users
module.exports = function (app) {
    app.post('/login', bodyParser, function (req, res) {
        console.log(req.body.login);
        const request = require('request');
        const url = 'http://localhost:8080/login';
        var answer = '';

        request.post({
            url: url,
            data: {
                loginData: req.body
                /*login: req.body.login,
                password: req.body.password*/
            }
        }, function (error, response, body) {
            if (error == null || response.statusCode == 200) {
                console.log("Response" + response.toString() + body.value);
            }else {
                console.log("Error");
            }
        });
        res.redirect("/chat");

    });
    app.get('/login', function (request, response) {
        response.render('login');

        // var lines = data.split('\n');
/*
        var result = [];
        for (var i = 0; i < lines.length; i++) {
            result.push({'name' : lines[i].split(' ')[0],
                'surname': lines[i].split(' ')[1]});
        }*/
        // response.setHeader('Content-Type', 'application/json');
        // response.send(JSON.stringify(result));

    });

    app.get('/chat', function (request, response) {
        response.render('chat');
        // вытаскиваю тело в формате JSON
        var body = request.body;
        console.log(body);

    });
};