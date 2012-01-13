package ss.project.noteforker.service.objectify;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ss.project.noteforker.mvc.model.domain.*;

import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify needs every persistable object to be registered. This class
 * registers all domain objects at the time when the {@link ServletContext} is
 * initialized.
 */
public final class ObjectifyInitializer implements ServletContextListener {
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		//ObjectifyService.register(Entity.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Note.class);
		ObjectifyService.register(FileIndex.class);
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		// do nothing
	}
}

