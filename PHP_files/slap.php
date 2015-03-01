<?
	$user = $_GET["username"];
?>
<html>
	<head>
		<meta id="meta" name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			body{
				background-image:url(http://mygaming.co.za/news/wp-content/uploads/2012/08/batman-slap-header.jpg); 
				background-size:auto 100%;
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
				font-size:6em;
				color:#f2f2f2;	
				text-shadow: 2px 2px 4px #000000;
			}
			.topMsg{
				position:absolute;
				top:40%;
				text-align: center;
				width:100%;
			}
			.btmMsg{
				position:absolute;
				top:40%;
				text-align: center;
				width:100%;
			}
		</style>
	</head>
	<body>
			<div class="msg"><div class="topMsg"><?=$user?></div></div>
			<div class="msg"><div class="btmMsg">IS DEAD</div></div>
	</body>
</html>