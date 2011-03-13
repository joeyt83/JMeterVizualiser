import com.newatlanta.commons.vfs.provider.gae.GaeVFS
import org.apache.commons.vfs.FileObject
import org.apache.commons.vfs.FileSystemManager
import javax.servlet.ServletContext

class BootStrap {

    def init = { ServletContext servletContext ->
		String rootPath = servletContext.getRealPath( "/" );
		GaeVFS.setRootPath( rootPath );

		FileSystemManager fsManager = GaeVFS.getManager();
		FileObject anonymousUsersDir = fsManager.resolveFile( "gae://users/anonymous" );
		if ( !anonymousUsersDir.exists() ) {
			anonymousUsersDir.createFolder();
		}
    }

    def destroy = {
		GaeVFS.clearFilesCache()
		GaeVFS.close()
    }
}
