<html>
<head>
<title>JMeterVizualiser files</title>
 <meta name="layout" content="main" />
<uploader:head />
</head>

<body>

<h1>Current List of Files</h1>

<g:each in="${files}" var="file">
        <p>${file}</p>
    </g:each>
	<h1>Upload your JMeter results file here</h1>
	<h2>In the file input</h2>



	<uploader:uploader id="ajaxUploader">
		
	</uploader:uploader>

<g:link controller="JMeterResultsFile" action="delete">
    Reset the files list
</g:link>
	
</body>
</html>