UPDATE:  1/26/2016

Refactored code to extract business logic from Servlet.  Created ResultsService and FileHandlerService interfaces and 
associated implementations.   

UploadServlet code went from 300+ lines of code to less than 100+. Easier to read and maintain.  Will be easier to write test cases

Next step is to convert this application to SpringMVC framework/pattern.


Original Info:
This web app will calculate the halfway distance of an activity to determine if activity was a negative split.
Why negative split?
Could not find a calculator online that would analyze a gps file to determine if an activity was a negative split.

First implementation will upload a Garmin .tcx file and determine if negative split.

Added error handling. tcx files do not always contain distance data in last gps trackpoint

App currently deployed on a Linux server on Linode- accessible via: 
www.negativesplitcalculator.com


Sample test files to use:
eugeneMarathonPosSplit.tcx
indyMarathonNegSplit.tcx
moablHalfPosSplit.tcx
moabHalfNegSplit.tcs


TODO: 
Add functionality for .gpx files
Add functionality to handle multisport files
Add functionality to upload 2 files for comparsion
Add graphing of the data to display available data