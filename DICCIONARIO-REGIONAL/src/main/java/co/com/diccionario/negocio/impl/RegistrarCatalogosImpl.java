package co.com.diccionario.negocio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Categoria;
import co.com.diccionario.document.Ciudad;
import co.com.diccionario.document.Departamento;
import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.CategoriaDTO;
import co.com.diccionario.dto.CiudadDTO;
import co.com.diccionario.dto.DepartamentoDTO;
import co.com.diccionario.dto.PaisesDTO;
import co.com.diccionario.mapper.CategoriaMapper;
import co.com.diccionario.mapper.CiudadMapper;
import co.com.diccionario.mapper.DepartamentoMapper;
import co.com.diccionario.mapper.PaisesMapper;
import co.com.diccionario.mongodb.iface.CiudadIface;
import co.com.diccionario.mongodb.iface.DepartamentoIface;
import co.com.diccionario.mongodb.iface.PaisesIface;
import co.com.diccionario.mongodb.repository.iface.CategoriaRepository;
import co.com.diccionario.mongodb.repository.iface.CiudadesRepository;
import co.com.diccionario.mongodb.repository.iface.DepartamentosRepository;
import co.com.diccionario.mongodb.repository.iface.PaisesRepository;
import co.com.diccionario.negocio.iface.RegistrarCatalogosIface;

@Service
public class RegistrarCatalogosImpl implements RegistrarCatalogosIface {

	@Autowired
	PaisesRepository paisesRepository;
	@Autowired
	DepartamentosRepository departamentosRepository;
	@Autowired
	CiudadesRepository ciudadesRepository;
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	PaisesIface paisesIface;
	@Autowired
	DepartamentoIface departamentoIface;
	@Autowired
	CiudadIface ciudadIface;

	@Override
	public void registraPaises(PaisesDTO paisesDTO) {
		Paises paises = PaisesMapper.INSTANCE.paisDTOToPaises(paisesDTO);
		/*
		 * se busca el maximo id del documento
		 */
		Paises lastPais = paisesIface.findLastId();
		if(lastPais == null){
			paises.setId(1);
		}else{
			paises.setId(lastPais.getId() + 1);
		}
		
		
		paisesRepository.save(paises);
	}

	@Override
	public void registrarDepartamento(DepartamentoDTO departamentoDTO) {
		Departamento departamento = DepartamentoMapper.INSTANCE.departamentoDTOToDepartamento(departamentoDTO);
		/*
		 * se busca el maximo id del documento
		 */
		Departamento lastDepa = departamentoIface.findLastId();
		if (lastDepa == null) {
			departamento.setId(1);
		} else {
			departamento.setId(lastDepa.getId() + 1);

		}
		departamentosRepository.save(departamento);
	}

	@Override
	public void registrarCiudad(CiudadDTO ciudadDTO) {
		Ciudad ciudad = CiudadMapper.INSTANCE.ciudadDTOToCiudad(ciudadDTO);
		/*
		 * se busca el maximo id del documento
		 */
		Ciudad lastCiudad = ciudadIface.findLastId();
		ciudad.setId(lastCiudad.getId() + 1);
		ciudadesRepository.save(ciudad);

	}

	@Override
	public void registrarCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoria = CategoriaMapper.INSTANCE.categoriDTOToCategoria(categoriaDTO);
		categoriaRepository.save(categoria);
	}

}
