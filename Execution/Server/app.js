const express = require('express'),
    app = express(),
    sql = require('mssql');

let connect = (f) => {
    sql.connect()
};

app.get('/', (req, res, next) => {
    res.json({ message: 'Here is nothing use url\'s /locations/all or /locations/add' });
});

app.get('/locations/all', (req, res, next) => {
    res.json({ data: 'some data' });
});

app.listen(3000, () => {
    console.log('Example app listening on port 3000!');
});