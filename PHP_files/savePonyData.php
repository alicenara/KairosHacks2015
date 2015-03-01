<?php
    require_once("includes/config.php");

    $username = $_GET['username'];
    $lat = $_GET['location'];
    $lat = explode(";",$lat);
    $long = $lat[1];
    $lat = $lat[0];

    mono_query("SELECT * FROM KHGame WHERE username LIKE '".$username."'", $people_playing,1);
    
    if(count($people_playing)==0 || is_null($people_playing)){
        mono_query("INSERT INTO KHGame VALUES(username, lat, long) (".$username.",".$lat.",".$long.")");
    }else{
        mono_query("UPDATE KHGame SET lat = '".$lat."', long = '".$long."' WHERE username LIKE '".$username."'");
    }

?>