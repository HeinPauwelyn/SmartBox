const express = require('express'),
    app = express(),
    sql = require('mssql'),
    config = require('./config');

let connect = (f, next) => {
    sql.connect(config.database.connectionstring).then(f).catch((err) => {
        next(err);
    });
};

app.get('/', (req, res, next) => {
    res.json({ message: 'Here is nothing use url\'s /locations/all or /locations/add' });
});

app.get('/locations/all', (req, res, next) => {

    let f = () => {
        new sql.Request().query('select * from Locations').then((recordSet) => {
            res.json(recordSet);
        }).catch((err) => {
            next(err);
        });
    };

    connect(f, next);
});

app.post('/locations/add', (req, res, next) => {

    let f = () => {
        new sql.Request().query('insert into Locations() values ()')
    }
});

app.listen(3000, () => {
    console.log('Example app listening on port 3000!');
});