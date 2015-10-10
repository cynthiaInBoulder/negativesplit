<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page isELIgnored="false" %>

<html>

    <head>
        
        <title>Negative Split Calculator</title>
        <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,300italic,300,400italic,600,600italic' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Signika:600' rel='stylesheet' type='text/css'>
        
        <link rel="stylesheet" href="css/normalize.css"/>
        <link rel="stylesheet" href="css/style.css"/>

    </head>

<body>

<div class="page-wrapper">
    <header class="header-wrapper container clearfix">
        <div class="header">
            <div class="navbar-wrapper clearfix">
                <ul class="navbar">
                    <li class="menu-item"><a href="index.html">Home</a></li>
                    <li class="menu-item"><a href="contact.html">Contact</a></li>
                    <li class="menu-item"><a href="about.html">About</a></li>
                </ul>
            </div>
            <div id="logo">
                <img src="img/negative-split-calculator.png" class="bordered-image calculator-logo">
                <h1>Negative Split Calculator <span>for Garmin or GPX files</span></h1>
            </div>
        </div>
    </header>


	<div class="clearfix wrapper" >
	  
	<!-- <table class="table table-striped table-hover table-condensed">
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
	    	
	    	
		</tr> -->

		<table class="table-full">
			<caption>Activity Results:  ${resultsData.result } </caption>
			<tr><td>
				<div class="results-wrapper">
			
				<!-- <div class="results-time">
						 <p>First Half</p> 
					<p >${resultsData.result } Split<p>
					</div>   -->
					<div class="results-number">
						<p>Elasped First half time:    ${resultsData.time1stHalf}</p>
					</div>
				</div>
				<div class="results-wrapper">
			<!--			<div class="results-time">
						<p>Second Half</p>
					</div>  -->
					<div class="results-number">
						<p>Elasped Second half time:  ${resultsData.time2ndHalf }</p>
					</div>
				</div>
				<div class="results-total">
					<p>Total Time:</p>
					<p>${resultsData.totalTime }</p>
				</div>
			</td></tr>
		</table>

	 	<table class="table-half table-left">
			<caption>Distance</caption>
			<tr>
				<td>
					<span class="table-subhead">50%</span>
					<span class="table-result">${resultsData.distance1stHalf }</span>
					<span class="table-label">mi</span>
				</td>
				<td>
					<span class="table-subhead">100%</span>
					<span class="table-result">${resultsData.distance2ndHalf }</span>
					<span class="table-label">mi</span>
				</td>
			</tr>
	 	</table>

	 	<table class="table-half table-right">
			<caption>Split Difference</caption>
			<tr>
				<td>
					<span class="table-result">${resultsData.split }</span>
					<span class="table-label">mi</span>
				</td>
				<td>
					<span class="table-result">${resultsData.percentage }%</span>
					<span class="table-label">Difference</span>
				</td>
			</tr>
	 	</table>

	</div>
</div><!-- /page-wrapper -->


<footer class="site-footer container">
    <p>&copy; Negative Split Calculator 2015</p>
</footer>


</body>

</html>

