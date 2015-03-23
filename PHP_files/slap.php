<?php
	$user = $_GET["username"];
	$user = explode(';',$user);
	$count  = $user[1];
	$user = $user[0];
?>
<html>
	<head>
		<meta id="meta" name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			body{
				background-image:url(http://25.media.tumblr.com/tumblr_m48hmjMuAD1rtir38o1_250.jpg); 
				background-size: auto 100%;
				background-repeat: no-repeat;
				background-position:center;
				width:100%; 
				height:100%;
				padding:0;
				margin:0;
				font-family: Verdana, Arial;
			}
			.msg{
				width:100%;
				height:50%;
				float:left;
				position:relative;
				text-align: center;
				font-size:100%;				
				font-weight: bolder;
				color:#f2f2f2;	
				text-shadow: -5px -5px 1px #000000, 5px 5px 1px #000000;
			}
			.topMsg{
				position:absolute;
				/*top:40%;*/
				font-size:100%;
				height: 100%;
				text-align: center;
				width:100%;
			}
			.btmMsg{
				position:absolute;
				top:40%;
				text-align: center;
				width:100%;
				font-size:500%;
			}
		</style>
	</head>
	<body>
			<div class="msg">
				<div class="topMsg"><?php echo $user ?></div>
				<div class="topMsg">IS DEAD</div>
			</div>
			<div class="msg"><div class="btmMsg"><?php echo $count ?> MORE ALIVE</div></div>
	</body>
</html>