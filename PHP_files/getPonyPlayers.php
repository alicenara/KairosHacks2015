<?php
    require_once("../includes/config.php");

    mono_query("SELECT username FROM KHGame WHERE active = 1 ORDER BY username",$people_playing,0);
    
    for($i=0;$i<count($people_playing);$i++){
        echo ($people_playing[$i]['username']);
        if($i<count($people_playing)-1) echo(";");
    }
?>