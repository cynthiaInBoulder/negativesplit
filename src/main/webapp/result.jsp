<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page isELIgnored="false" %>

<html>
    <head>
        
        <title>Negative Split Calculator</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap/css/joural-bootstrap.min.css"/>                                           
        <link rel="stylesheet" href="css/site.css"/>

    </head>

<body>  

	 <header class="container">
	  <div id="menu" class="navbar">
	 	<div id="logo">
		 <ul>
		  <li><a href="index.html">Home</a></li>
		  <li><a href="contact.html">Contact</a></li>
		  <li><a href="about.html">About</a></li>
		  </ul>
		</div>  
   		<div><img src="img/negative-split-calculator.png" class="bordered-image"><strong>Results</strong></div>
   	  </div>
	</header>

<div class="container" >
  
<table class="table table-striped table-hover table-condensed">
	<tr>
		<th> Total Distance</th>
		<th> Total Time</th>
		<th> Distance 1st half</th>
		<th> Time 1st half</th>
		<th> Distance 2nd half</th>
		<th> Time 2nd half</th>
		<th> Result</th>
		<th> Time Difference</th>
		<th> Percent Difference</th>
		
	</tr>

	<tr>    

    	<td>${resultsData.totalDistance}</td>
    	<td>${resultsData.totalTime }</td>
    	<td>${resultsData.distance1stHalf }</td>
    	<td>${resultsData.time1stHalf}</td>
    	<td>${resultsData.distance2ndHalf }</td>
    	<td>${resultsData.time2ndHalf }</td>
    	<td>${resultsData.result }</td>
    	<td>${resultsData.split }</td>
    	<td>${resultsData.percentage }%</td>
    	
    	
	</tr>
 	</table>
 	
 	<p>${resultsData.estDist}</p>
 
 

</div>
	
</body>

</html>

