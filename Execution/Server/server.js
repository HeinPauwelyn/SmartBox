const http = require('http'),
      fs = require("fs"),
      path = require("path");

let port = process.env.port || 8181,
    extentions = {
        ".js": "text/javascript",
        ".jpg": "text/jpeg",
        ".gif": "text/gif",
        ".json": "application/json"
    };

http.createServer(function (req, res) {

    if (req.url === "/" || req.url === "") {

        req.url = "index.html";
    }

    let filename = path.basename(req.url),
        ext = path.extname(filename),
        localPath = path.normalize(process.cwd() + "/05 - Streams/public/" + req.url);

    var readFile = function(err, content) {

        if (err) {

            res.writeHead(500, { 'Content-Type': 'text/html' });
            res.write("<p><b>500: Server error:</b></p><pre>" + err.message + "</pre>");
            res.end();
        }
        else {
            res.writeHead(200, { 'Content-Type': extentions[ext] });
            res.end(content);
        }
    };

    var checkFile = function(err, stats) {
            
        if(stats.isFile()) {

            console.log("rendering page", localPath);
            fs.readFile(localPath, readFile);
        }
        else {
            console.error("not a file");     
        }
    };

    if (extentions[ext]) {
        fs.stat(localPath, checkFile);
    }

}).listen(port);