package co.com.diccionario.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("selectItemsConverter")
public class SelectItemsConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}

		return fromSelect(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return serialize(value);
	}

	private String serialize(final Object object) {
		if (object == null) {
			return null;
		}
		return object.getClass() + "@" + object.hashCode();
	}

	@SuppressWarnings("unchecked")
	private Object fromSelect(final UIComponent currentcomponent, final String objectString) {

		if (currentcomponent.getClass() == UISelectItem.class) {
			final UISelectItem item = (UISelectItem) currentcomponent;
			final Object value = item.getValue();
			if (objectString.equals(serialize(value))) {
				return value;
			}
		}

		if (currentcomponent.getClass() == UISelectItems.class) {
			final UISelectItems items = (UISelectItems) currentcomponent;
			final List<Object> elements = ((List<Object>) items.getValue());
			for (final Object element : elements) {
				if (objectString.equals(serialize(element))) {
					return element;
				}
			}
		}

		if (!currentcomponent.getChildren().isEmpty()) {
			for (final UIComponent component : currentcomponent.getChildren()) {
				final Object result = fromSelect(component, objectString);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	
}
