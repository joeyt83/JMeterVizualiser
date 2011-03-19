package jmetervizualiser

import javax.servlet.http.Cookie

class VisualisationController {

     def index = {
		setResultsFileCookie(params.resultsfile)
		render(text: "choose a visualisation")
	 }

	def timeseries = {

    }

    private void setResultsFileCookie(String resultsFile) {
        def cookie = new Cookie('resultsfile', resultsFile)
        response.addCookie(cookie)
    }
}
