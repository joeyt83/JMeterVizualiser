<html>
<head>
<title>JMeterVizualiser files</title>
 <meta name="layout" content="main" />
<uploader:head />
</head>

<body>

<h1>Current List of Files</h1>

<g:each in="${files}" var="file">
        <p>${file} : <g:link controller="visualisation" params="${['resultsfile': file]}">visualise this</g:link></p>
    </g:each>
	<h1>Upload your JMeter results file here</h1>
	<h2>In the file input</h2>



	<uploader:uploader url="create" id="ajaxUploader">
		
	</uploader:uploader>

<g:link controller="file" action="delete">
    Reset the files list
</g:link>
	
</body>
</html>