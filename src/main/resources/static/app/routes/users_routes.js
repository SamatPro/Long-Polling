bodyParser = require('body-parser').json();

module.exports = function (app) {
    var a_token;
    app.post('/login', bodyParser, function (req, res) {
        const axios = require('axios');
        const url = 'http://localhost:8080/login';
        console.log(JSON.stringify(req.body));

        axios.post(url, req.body).then(
            function (value) {
                a_token = value.data.value;
                console.log(a_token);
                res.redirect("/chat");
            }
        ).catch(function (reason) {
            console.log(reason);
            res.redirect("/login");
        })
    });
    app.get('/login', function (request, response) {
        response.render('login');
    });

    app.get('/chat', function (request, response) {

        response.render('chat', {'token': a_token});
        var body = request.body;
        console.log(body);

    });

    app.get('/messages', function (request, response) {
        const axios = require('axios');
        const url = 'http://localhost:8080/messages';
        var answer = '';
        console.log(request);

        axios.get(url, request.body).then(
            function (value) {
                console.log(value.data.value);

                var token = value.data.value;

                localStorage.setItem ('token', token);
                axios.defaults.headers.common['AUTH'] = token;
                res.redirect("/chat");
            }
        ).catch(function (reason) {
            console.log(reason);
            res.redirect("/login");
        })
    });

    app.post('/messages', bodyParser,function (request, response) {
        const axios = require('axios');
        const url = 'http://localhost:8080/messages';
        console.log(JSON.stringify(request.body));
        axios.post(url, JSON.stringify(request.body)).then(
            function (value) {
                // console.log(value.data.value);

                // res.redirect("/chat");
            }
        ).catch(function (reason) {
            // console.log(reason);
            // res.redirect("/login");
        });
        // console.log(request.body);
        // console.log(JSON.stringify(request.body));
    });

};