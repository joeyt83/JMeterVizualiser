package jmetervizualiser

class VisualisationController {

     def index = {
		println "redirecting to next action"
		redirect(action: timeseries)
	}

	def timeseries = {

	}
}
