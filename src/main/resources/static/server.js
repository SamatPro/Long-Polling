
const express = require('express');
// создаем экземпляр объекта
const app = express();
// // для работы с файловым хранилищем
// const fs = require();
// для обработки тела post-запросов
const bodyParser = require('body-parser');
// подключаем его к модулю express
app.use(bodyParser.urlencoded({ extended: true }));
require('./app/routes')(app);
// вызываем функцию для обработки маршутов из папки
// говорим, что раздаем папку public
app.use(express.static('public'));

// app.set('views', '/public/html');
app.set('views', __dirname + '/public/html');
app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');

/*
app.get('/login', function (req, res) {
    res.render('login');
});*/



app.listen(80);
console.log("Server started at 80");
