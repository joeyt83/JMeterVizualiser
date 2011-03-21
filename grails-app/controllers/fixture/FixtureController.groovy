package fixture

import com.google.appengine.api.users.UserService
import com.google.appengine.api.users.UserServiceFactory

class FixtureController {

    def index = { }

    def meh = {
        UserService userService = UserServiceFactory.getUserService();
        if (request.getUserPrincipal()) {
            render(text: """
            <p>Hello, ${request.getUserPrincipal().getName()}!
            You can <a href=\"${userService.createLogoutURL(request.getRequestURI())}\">sign out</a>.</p>
            <p>${request.getRequestURI()}</p>
            <p>${request.getRequestURL()}</p>
            """)
        } else {
            render(text: """You can <a href=\"${userService.createLoginURL('/fixture/meh')}\">sign in</a>.</p>
            <p>${request.getRequestURI()}</p>
            <p>${request.getRequestURL()}</p>
            """)
        }
    }
}
