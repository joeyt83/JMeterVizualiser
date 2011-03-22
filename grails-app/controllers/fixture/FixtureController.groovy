package fixture

import com.google.appengine.api.users.UserService
import com.google.appengine.api.users.UserServiceFactory
import org.apache.commons.vfs.FileObject
import jmetervizualiser.GAEVfsAwareController
import java.util.zip.GZIPInputStream

class FixtureController extends GAEVfsAwareController {

    def index = { }

    def loginhackery = {
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

    def exploreFileSystem = {
        List fsTree = []
        FileObject rootDir = getRootDirectory()
        recursivelyPrintFileSystem(fsTree, rootDir)
        String response = ''
        fsTree.each {
            response += "<p><a href='/fixture/showFileContents?path=${it}'>${it}</a> <a href='/fixture/deleteFile?path=${it}'>delete</a></p>"
        }
        render response
    }

    def showFileContents = {

        FileObject file = getFileByPath(params.path)
        response.getOutputStream().leftShift(file.getContent().getInputStream().getText())
        render response
    }

    def deleteFile = {
        FileObject file = getFileByPath(params.path)
        file.delete()
        redirect(action: 'exploreFileSystem')
    }

    def unzipFile = {
        String originalFilePath = params.path
        FileObject zippedFile = getFileByPath(originalFilePath)

        GZIPInputStream gzipInputStream = new GZIPInputStream(zippedFile.getContent().getInputStream());

        String unzippedFilePath = originalFilePath.replace(".gz", "");
        FileObject unzippedFile = getFileByPath(unzippedFilePath)
        unzippedFile.createFile()

        OutputStream out = unzippedFile.getContent().getOutputStream()

        byte[] buf = new byte[1024];
        int len;
        while ((len = gzipInputStream.read(buf)) > 0)
            out.write(buf, 0, len);

        gzipInputStream.close();
        out.close();

        render text: "File unzipped to ${unzippedFilePath}"
    }

private void recursivelyPrintFileSystem(List fsTree, FileObject file) {

    file.children.each { FileObject child ->
        fsTree.add child.name.path
        recursivelyPrintFileSystem(fsTree, child)
    }
}

}
