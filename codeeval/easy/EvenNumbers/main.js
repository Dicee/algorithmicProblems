package codeeval.easy.EvenNumbers;

var fs  = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function (line) {
    console.log((parseInt(line) + 1) % 2);
});