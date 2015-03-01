<?php
    require_once("../includes/config.php");

    $lat = $_GET['link'];
    $lat = explode(";",$lat);
    $username = $lat[0];
    $long = $lat[2];
    $lat = $lat[1];

    //check if there is this player in the bd
    mono_query("SELECT * FROM KHGame WHERE username LIKE '".$username."'", $people_playing,1);
 
    if(count($people_playing)==0 || is_null($people_playing)){
        mono_query("INSERT INTO KHGame (username, lat, lng) VALUES ('".$username."','".$lat."','".$long."')",$result,1);
    }else{
        mono_query("UPDATE KHGame SET lat = '".$lat."', lng = '".$long."' WHERE username LIKE '".$username."'",$result,1);
    }

?>