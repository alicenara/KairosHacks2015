<?php
    require_once("../includes/config.php");

    $username = $_GET['username'];
    $lat = $_GET['location'];
    $lat = explode(";",$lat);
    $long = $lat[1];
    $lat = $lat[0];

    //check if there is this player in the bd
    mono_query("SELECT * FROM KHGame WHERE username LIKE '".$username."'", $people_playing,1);
 
    if(count($people_playing)==0 || is_null($people_playing)){
        mono_query("INSERT INTO KHGame (username, lat, lng) VALUES ('".$username."','".$lat."','".$long."')",$result,1);
    }else{
        mono_query("UPDATE KHGame SET lat = '".$lat."', lng = '".$long."', dead = 0 WHERE username LIKE '".$username."'",$result,1);
    }

?>