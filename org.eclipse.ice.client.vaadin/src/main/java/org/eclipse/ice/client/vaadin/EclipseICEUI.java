package org.eclipse.ice.client.vaadin;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.ice.core.iCore.ICore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("EclipseICETheme")
public class EclipseICEUI extends UI {

	/**
	 * Constructor
	 * @author Jay Jay Billings
	 *
	 */
    @WebServlet(urlPatterns = "/*", name = "EclipseICEUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EclipseICEUI.class, productionMode = false)
    public static class EclipseICEUIServlet extends VaadinServlet {
    }

	private BundleContext context;
	private HttpService httpService;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }
    
    @Activate
	public void start(BundleContext context) {
		System.out.println("VAADIN bundle started.");
		this.context = context;
		
		System.out.println("VAADIN Grabbed " + httpService.toString());
		Dictionary<String, String> servletParams = new Hashtable<>();
		servletParams.put("javax.ws.rs.Application", EclipseICEUIServlet.class.getName());
		Bundle bundle = null;
		bundle = context.getBundle();
		// Find the root location and the jaas_config file
		URL resourceURL = bundle.getEntry("");
		URL configFileURL = bundle.getEntry("jaas_config.txt");
		try {
			// Resolve the URLs to be absolute
//			resourceURL = FileLocator.resolve(resourceURL);
//			configFileURL = FileLocator.resolve(configFileURL);
//			HttpContext httpContext = new BasicAuthSecuredContext(resourceURL, configFileURL, "ICE UI");
			httpService.registerServlet("/", new EclipseICEUIServlet(), null, null);
		//	System.out.println("Resource = " + httpContext.getResource("VAADIN").toString());
		} catch (ServletException | NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
    
    public void setService(HttpService httpService) {
    	this.httpService = httpService;
    }

    public void setCore(ICore core) {
    	System.out.println("VAADIN Grabbed " + core.toString());
    	System.out.println(core.getAvailableItemTypes().getList());
    }
    
}
