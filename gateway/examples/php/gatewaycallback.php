<?php
   if($_GET['errorCode']) {
   		//process gateway error
   }else{
   	    if($_GET['userInfo']) {
   	    	$userInfo = utf8_decode(base64_decode($_GET['userInfo']));
   	    }
   }
?>