<?php
    require_once("../includes/config.php");

    $lat = $_GET['link']; //link=username;lat;long
    $lat = explode(";",$lat);
    $username = $lat[0];
    $check_user = $lat[3];
    $long = $lat[2];
    $lat = $lat[1];

    //TODO: check if players exists

    //save lat long
    mono_query("UPDATE KHGame SET lat = '".$lat."', lng = '".$long."' WHERE username LIKE '".$username."'",$result,1);

    //check user
    mono_query("SELECT * FROM KHGame WHERE username LIKE '".$check_user."'", $trying_to_kill,0);

    if($trying_to_kill[0]['dead']==0){
        $distance = distanceBetweenPeople($trying_to_kill[0]['lat'], $trying_to_kill[0]['lng'], $lat, $long);
        echo $distance;
        if($distance < 10){ //10 meters
            mono_query("UPDATE KHGame SET dead = 1 WHERE username LIKE '".$check_user."'",$result,1);
            header("https://api.justyo.co/yoall/api_token=c6537cd9-5fb8-41a7-be7b-03535363fdc0&link=http://elendow.com/KairosHacks2015/slap.php?username=".$check_user);
        } 

        mono_query("SELECT username FROM KHGame WHERE dead = 0 ORDER BY username",$people_playing,0);
        if(count($people_playing)==1){ //end of the game
            header("https://api.justyo.co/yoall/api_token=c6537cd9-5fb8-41a7-be7b-03535363fdc0&link=http://elendow.com/KairosHacks2015/slap.php?username=".$check_user);
            mono_query("DELETE FROM KHGame",$result,1);
        }
    }

    function distanceBetweenPeople($lat1, $lon1, $lat2, $lon2){ 
        $R = 63781370; // Radius of earth in m
        $dLat = deg2rad ($lat2 - $lat1);
        $dLon = deg2rad ($lon2 - $lon1);
        $lat1 = deg2rad ($lat1);
        $lat2 = deg2rad ($lat2);
        $lon1 = deg2rad ($lon1);
        $lon2 = deg2rad ($lon2);
        $calc = sin($dLat/2) * sin($dLat/2) + cos($lat1) * cos($lat2) * sin($dLon/2) * sin($dLon/2);
        $calc2 = 2 * atan2(sqrt($calc), sqrt(1-$calc));
        return ($R * $calc2)/100; // meters
    }
?>