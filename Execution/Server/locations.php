<?php
header('Content-Type: application/json');

var $device = $_GET("device");

/*
1. Connecteer naar Mongo database 
2. Selecteer data van dabase 
3. Genereer JSON
*/


print("[{ \"latitude\": 5049.8049, \"longitude\": 308.9925, \"altitude\": 0, \"time\": 85415 }, { \"latitude\": 5049.7522, \"longitude\": 308.9848, \"altitude\": 0, \"time\": 85518 }]");
?>