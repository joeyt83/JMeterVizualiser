<html>
<head>
<title>JViz timeseries Visualisation</title>
 <meta name="layout" content="main" />
 <g:javascript src="highcharts/adapters/prototype-adapter.js" />
 <g:javascript src="highcharts/highcharts.js" />
 <g:javascript>
 var chart1; // globally available
 var options = {
				chart: {
					renderTo: 'timeseries-container',
					zoomType: 'x',
					spacingRight: 20
				},
			    title: {
					text: 'Average response time for Jmeter test'
				},
			    subtitle: {
					text: document.ontouchstart === undefined ?
						'Click and drag in the plot area to zoom in' :
						'Drag your finger over the plot to zoom in'
				},
				xAxis: {
					type: 'datetime',
					maxZoom: 5,
					title: {
						text: 'time'
					}
				},
				yAxis: {
					title: {
						text: 'Response time (ms)'
					},
					min: 0,
					startOnTick: false,
					showFirstLabel: false
				},
				tooltip: {
					shared: true					
				},
				legend: {
					enabled: false
				},
				plotOptions: {
					area: {
						fillColor: {
							linearGradient: [0, 0, 0, 300],
							stops: [
								[0, '#4572A7'],
								[1, 'rgba(2,0,0,0)']
							]
						},
						lineWidth: 1,
						marker: {
							enabled: true,
							states: {
								hover: {
									enabled: true,
									radius: 5
								}
							}
						},
						shadow: false,
						states: {
							hover: {
								lineWidth: 1						
							}
						}
					}
				},
			
				series: []
			};

         function updateOptions(timeSeries) {
            options.series = [{
					type: 'area',
					name: 'New Average Response time',
					pointInterval: timeSeries.pointInterval,
					pointStart: Date.parse(timeSeries.pointStart),
					data: timeSeries.data
				}]

         }
      
Event.observe(document, 'dom:loaded', function() {

    new Ajax.Request('/data/averageResponseTimeOverTime', {
      method: 'get',
      onSuccess: function(transport) {
          var timeSeries = transport.responseText.evalJSON();

          updateOptions(timeSeries);
          chart1 = new Highcharts.Chart(options);
      }
    });


      
   });
 </g:javascript>
</head>

<body>
	<h1>Here's a chart</h1>
	<h2>Of type timeseries</h2>
    <h3>for file ${filename}</h3>
	
	<div id="timeseries-container" style="width: 100%; height: 400px"></div>

	
	
</body>
</html>