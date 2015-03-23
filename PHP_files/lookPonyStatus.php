<?php
    require_once("../includes/config.php");

    function checkPeople(){
        //get people status
        mono_query("SELECT * FROM KHGame WHERE active = 1 ORDER BY username",$people_playing,0);

        for($i=0;$i<count($people_playing);$i++){
            if($people_playing[$i]['dead']){
                echo ("<li><img src='http://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Blood.gif/45px-Blood.gif'/> ".$people_playing[$i]['username']." </li>");
            }else{
                echo ("<li><img src='http://i0.wp.com/kickerdaily.com/wp-content/uploads/2014/12/Teletubbies-baby-s_3147210b-e1419458598851.jpg?resize=40%2C40'/> ".$people_playing[$i]['username']. " - Localization: Lat=".$people_playing[$i]['lat']." Lon=".$people_playing[$i]['lng']." </li>");
            }
        }
    }

    if($_GET['id'] == "rnd"){
        checkPeople();
        exit;
    }
?>

<html>
    <head>
    <script type="text/javascript" src="../js/jquery-latest.js"></script>
    <script type="text/javascript">
        $( document ).ready(function(){
            $("#xuliList").html("<?php checkPeople(); ?>");
            setInterval(function(){
                $.get('lookPonyStatus.php?id=rnd', function(data){
                    $("#xuliList").html(data);
                });                
            }, 2000);
        });        
    </script>
    </head>
    <body>
        <ul id="xuliList"></ul>
    </body>
</html>