package co.com.diccionario.mongodb.iface;

import java.util.List;

import co.com.diccionario.document.Categoria;

public interface CategoriaIface {

	public List<Categoria> findByNombreLike(String nombre);

}
