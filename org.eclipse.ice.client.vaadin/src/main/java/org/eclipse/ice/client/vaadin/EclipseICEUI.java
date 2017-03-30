package org.eclipse.ice.client.vaadin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.eclipse.ice.core.iCore.ICore;
import org.eclipse.ice.datastructures.ICEObject.Identifiable;
import org.eclipse.ice.datastructures.form.DataComponent;
import org.eclipse.ice.datastructures.form.Form;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class EclipseICEUI extends UI {

	/**
	 * Constructor
	 * 
	 * @author Jay Jay Billings
	 *
	 */
	@WebServlet(urlPatterns = "/ice/forms/*", name = "EclipseICEUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = EclipseICEUI.class, productionMode = false)
	public static class EclipseICEUIServlet extends VaadinServlet {
	}

	private HttpService httpService;
	private static ICore coreService;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		String itemName = "Service is null";
		if (coreService != null) {
			itemName = coreService.getAvailableItemTypes().getList().get(0);
		}

		String id = coreService.createItem("Nek5000 Model Builder");
		Form form = coreService.getItem(Integer.valueOf(id));

		Panel p = new Panel("Nek5000 Model");
		p.setSizeUndefined();
		
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout hLayout = new HorizontalLayout();
		
		Button saveButton = new Button("Save");
		saveButton.addClickListener(e -> {
			coreService.updateItem(form, 1);
		});
		
		layout.addComponent(saveButton);
		layout.addComponent(hLayout);

		// final TextField items = new TextField();
		((DataComponent) form.getComponent(1)).retrieveAllEntries().get(0)
				.setValue(((DataComponent) form.getComponent(1)).retrieveAllEntries().get(0).getAllowedValues().get(0));
		coreService.updateItem(form, 1);

		form.getComponents().forEach(comp -> {
			if (comp instanceof DataComponent) {
				hLayout.addComponent(new DataComponentLayout((DataComponent) comp));
			}
		});
		
		hLayout.setMargin(true);
		hLayout.setSpacing(true);
		layout.setMargin(true);
		layout.setSpacing(true);

		p.setContent(layout);
		setContent(p);
	}

	/**
	 * OSGi bundle activator with annotation instead of activator class.
	 * 
	 * @param context
	 */
	@Activate
	public void start(BundleContext context) {
		System.out.println("VAADIN bundle started.");
		try {
			httpService.registerServlet("/ice/forms", new EclipseICEUIServlet(), null, null);
		} catch (ServletException | NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Acquire the Core service if it is available
		ServiceReference<ICore> iCoreServiceRef = context.getServiceReference(ICore.class);
		if (iCoreServiceRef != null) {
			System.out.println("Retrieving coreService for the client.");
			coreService = context.getService(iCoreServiceRef);
			System.out.println("Core service set.");
		} else {
			// Failure to get the core is a catastrophic error.
			System.out.println("Unable to access core!.");
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
