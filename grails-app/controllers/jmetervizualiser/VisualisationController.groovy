package jmetervizualiser

import javax.servlet.http.Cookie

class VisualisationController {

     def index = {
		setResultsFileCookie(params.resultsfile)
        render(view: 'choosevisualisation')
	 }

	def timeseries = {

    }

    private void setResultsFileCookie(String resultsFile) {
        def cookie = new Cookie('resultsfile', resultsFile)
        cookie.setPath('/')
        response.addCookie(cookie)
    }
}
