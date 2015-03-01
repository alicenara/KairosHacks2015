<?php
    require_once("../includes/config.php");

    $username = $_GET['username'];
    $lat = $_GET['link'];
    $lat = explode(";",$lat);
    $check_user = $lat[0];
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
        /*if($distance < 5){ //5 meters
            mono_query("UPDATE KHGame SET dead = 1 WHERE username LIKE '".$check_user."'",$result,1);
            //enviar yo

        } */
    }

    private function distanceBetweenPeople($lat1, $lon1, $lat2, $lon2){ 
        $R = 6378.137; // Radius of earth in KM
        $dLat = ($lat2 - $lat1) * M_PI / 180;
        $dLon = ($lon2 - $lon1) * M_PI / 180;
        $calc = sin($dLat/2) * sin($dLat/2) + cos($lat1 * M_PI / 180) * cos($lat2 * M_PI / 180) * sin($dLon/2) * sin($dLon/2);
        $calc2 = 2 * atan2(sqrt($calc), sqrt(1-$calc));
        $result = $R * $calc2;
        return $result * 1000; // meters
    }
}
?>