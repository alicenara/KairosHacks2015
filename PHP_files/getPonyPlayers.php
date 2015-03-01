<?php
    require_once("../includes/config.php");

    mono_query("SELECT username FROM KHGame ORDER BY username",$people_playing,0);
    
    for($i=0;$i<count($people_playing);$i++){
        echo ($people_playing[$i]['username']);
        if($i<count($people_playing)) echo(";");
    }
?>