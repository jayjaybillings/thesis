package org.eclipse.ice.client.vaadin;

import org.eclipse.ice.datastructures.ICEObject.IUpdateable;
import org.eclipse.ice.datastructures.ICEObject.IUpdateableListener;
import org.eclipse.ice.datastructures.entry.ContinuousEntry;
import org.eclipse.ice.datastructures.entry.DiscreteEntry;
import org.eclipse.ice.datastructures.entry.ExecutableEntry;
import org.eclipse.ice.datastructures.entry.FileEntry;
import org.eclipse.ice.datastructures.entry.IEntryVisitor;
import org.eclipse.ice.datastructures.entry.MultiValueEntry;
import org.eclipse.ice.datastructures.entry.StringEntry;
import org.eclipse.ice.datastructures.form.DataComponent;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;



public class DataComponentLayout extends VerticalLayout implements IEntryVisitor, IUpdateableListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataComponent component;
	
	public DataComponentLayout(DataComponent comp) {
		super();
		component = comp;
		component.retrieveAllEntries().forEach(entry -> {
			entry.accept(this);
		});
		
		setMargin(true);
		setSpacing(true);
		
		component.register(this);
		setCaption(component.getName());
	}

	@Override
	public void visit(StringEntry entry) {
		// TODO Auto-generated method stub
		TextField text = new TextField();
		text.setCaption(entry.getName());
		text.setDescription(entry.getDescription());
		text.setValue(entry.getValue() == null ? entry.getDefaultValue() : entry.getValue());
		text.addTextChangeListener(event ->	entry.setValue(event.getText()));
		addComponent(text);
	}

	@Override
	public void visit(DiscreteEntry entry) {
		// TODO Auto-generated method stub
		ComboBox dropdown = new ComboBox();
		dropdown.setCaption(entry.getName());
		dropdown.setDescription(entry.getDescription());
		dropdown.addItems(entry.getAllowedValues());
		dropdown.addValueChangeListener(e -> entry.setValue((String)dropdown.getValue()));
		dropdown.setValue(entry.getAllowedValues().get(0));
		addComponent(dropdown);
	}

	@Override
	public void visit(ExecutableEntry entry) {
		// TODO Auto-generated method stub
		System.out.println("ExecutableEntry not implemented");
	}

	@Override
	public void visit(ContinuousEntry entry) {
		// TODO Auto-generated method stub
		System.out.println("ContinuousEntry not implemented");
	}

	@Override
	public void visit(FileEntry entry) {
		// TODO Auto-generated method stub
		System.out.println("FileEntry not implemented");
	}

	@Override
	public void visit(MultiValueEntry entry) {
		// TODO Auto-generated method stub
		System.out.println("MultiValueEntry not implemented");
	}

	@Override
	public void update(IUpdateable dataComponent) {
		System.out.println("UPDATING " + dataComponent.getName());
		getUI().getSession().getLockInstance().lock();
		try {
			removeAllComponents();
			component.retrieveAllEntries().forEach(entry -> {
				entry.accept(this);
			});
		} finally {
			getUI().getSession().getLockInstance().unlock();
		}
	}
}
