const express = require('express'),
    app = express(),
    sql = require('mssql'),
    config = require('./config'),
    bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json({ type: 'application/json' }));

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
        new sql.Request().input('Latitude', sql.NVarChar, req.body.latitude)
            .input('Longitude', sql.NVarChar, req.body.longitude)
            .input('Altitude', sql.NVarChar, req.body.altitude)
            .input('Time', sql.NVarChar, req.body.timestamp)
            .input('IsOpen', sql.NVarChar, req.body.isopen)
            .execute('insertLocation')
            .then((resordSet) => {
                res.json({ message: 'The row is append to the table' })
            })
            .catch((err) => {
                next(err);
            });
    };

    connect(f, next);
});

app.listen(3000, () => {
    console.log('Example app listening on port 3000!');
});