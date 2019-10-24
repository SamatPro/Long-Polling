// подключаем модуль users_routes
const usersRoutes = require('./users_routes');
// создаем функцию для export-а, именно ее вызываем в server.js
module.exports = function(app) {
    // вызываем функцию из users_routes для обработки запросов
    usersRoutes(app);
};
console.log("Hello Samat!");