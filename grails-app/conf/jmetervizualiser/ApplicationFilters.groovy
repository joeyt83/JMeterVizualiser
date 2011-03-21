package jmetervizualiser

import javax.servlet.http.Cookie

class ApplicationFilters {

    def filters = {
        attachResultsFileToController(controller:'data', action:'*') {
            before = {
                List<Cookie> cookies = request.getCookies()

                Cookie resultsFileCookie = cookies.find { Cookie cookie ->
                    cookie.name == 'resultsfile'
                }

                if(resultsFileCookie && resultsFileCookie.value != '') {
                     request.resultsFile = resultsFileCookie.value
                } else {
                    render(text: "No results file found")
                    return false
                }
                
            }
            after = {
                
            }
            afterView = {
                
            }
        }
    }
    
}
