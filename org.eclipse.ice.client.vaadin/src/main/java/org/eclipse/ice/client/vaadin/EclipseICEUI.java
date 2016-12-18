package org.eclipse.ice.client.vaadin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.eclipse.ice.core.iCore.ICore;
import org.eclipse.ice.datastructures.ICEObject.Identifiable;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

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

	private HttpService httpService;
	private ICore coreService;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        System.out.println("VAADIN Initialized.");
        
        final TextField name = new TextField();
        name.setCaption("ICE - Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        final TextField items = new TextField();
        String itemName = "Service is null";
        if (coreService != null) {
        	itemName = coreService.getAvailableItemTypes().getList().get(0);
        }
        items.setCaption(itemName);
        
        layout.addComponents(name, button, items);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }
    
    /**
     * OSGi bundle activator with annotation instead of activator class.
     * @param context
     */
    @Activate
	public void start(BundleContext context) {
		System.out.println("VAADIN bundle started.");
		try {
			httpService.registerServlet("/", new EclipseICEUIServlet(), null, null);
		} catch (ServletException | NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
    
    public void setService(HttpService httpService) {
    	this.httpService = httpService;
    }
    
    public void setCoreService(ICore coreService) {
    	System.out.println("Core status service = " + coreService);
    	this.coreService = coreService;
    }
    
}
