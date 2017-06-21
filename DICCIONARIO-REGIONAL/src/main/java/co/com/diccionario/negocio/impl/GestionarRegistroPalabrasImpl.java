package co.com.diccionario.negocio.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import co.com.diccionario.document.Palabras;
import co.com.diccionario.document.Sinonimos;
import co.com.diccionario.dto.PalabrasDTO;
import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.mapper.SinonimosMapper;
import co.com.diccionario.mongodb.iface.CounterImagenesIface;
import co.com.diccionario.mongodb.repository.iface.SinonimosRepository;
import co.com.diccionario.negocio.iface.GestionarRegistroPalabrasIface;
import co.com.diccionario.utilidades.FileUtils;

@Service
public class GestionarRegistroPalabrasImpl implements GestionarRegistroPalabrasIface {

	@Autowired
	SinonimosRepository sinonimosRepository;
	@Autowired
	Environment env;
	@Autowired
	CounterImagenesIface counterImagenesIface;

	@Override
	public SinonimosDTO actualizarSinonimos(SinonimosDTO sinonimosDTO) {
		Sinonimos sinonimos = SinonimosMapper.INSTANCE.sinonimoDTOToSinonimo(sinonimosDTO);

		/**
		 * agrega el sinonimo normal
		 */
		Sinonimos sinonimosInicial = sinonimosRepository.save(sinonimos);

		/**
		 * agregamos el sinonimo al contrario
		 */
		String paisOrigen = sinonimos.getPaisDestino();
		String paisDestino = sinonimos.getPaisOrigen();
		String categoria = sinonimos.getCategoria();
		List<Palabras> valuesSinonimos = sinonimos.getSinonimos();
		String termino = "";
		if (valuesSinonimos != null && !valuesSinonimos.isEmpty()) {
			Palabras palabra = valuesSinonimos.get(valuesSinonimos.size() - 1);
			termino = palabra.getPalabra();
		}

		List<Sinonimos> listSinonimos = sinonimosRepository
				.findByPaisOrigenAndPaisDestinoAndTerminoAndCategoria(paisOrigen, paisDestino, termino, categoria);
		if (listSinonimos != null && !listSinonimos.isEmpty()) {
			sinonimos = listSinonimos.get(0);
			String sinonimo = sinonimosDTO.getTermino();
			List<Palabras> listTerminoComoSinonimo = new ArrayList<>();
			Palabras palabra = new Palabras();
			palabra.setPalabra(sinonimo);
			listTerminoComoSinonimo.add(palabra);
			sinonimos.setTermino(termino);
			sinonimos.setSinonimos(listTerminoComoSinonimo);
		} else {
			sinonimos.setPaisDestino(paisDestino);
			sinonimos.setPaisOrigen(paisOrigen);
			String sinonimo = sinonimosDTO.getTermino();
			List<Palabras> listTerminoComoSinonimo = new ArrayList<>();
			Palabras palabra = new Palabras();
			palabra.setPalabra(sinonimo);
			listTerminoComoSinonimo.add(palabra);
			valuesSinonimos = sinonimos.getSinonimos();
			sinonimos.setTermino(termino);
			sinonimos.set_id(null);
			sinonimos.setOraciones(null);
			sinonimos.setDefiniciones(null);
			sinonimos.setSinonimos(listTerminoComoSinonimo);
		}
		sinonimosRepository.save(sinonimos);

		SinonimosDTO sinonimosResult = SinonimosMapper.INSTANCE.sinonimosToSinonimoDTO(sinonimosInicial);
		return sinonimosResult;
	}

	public void registrarSinonimosPalabrasCompleta(ParametrosRegistroTermino params) {
		byte[] imagen = params.getImagen();
		SinonimosDTO sinonimosDTO = params.getSinonimosDTO();
		if (imagen != null) {
			String rutaMainImagenes = env.getProperty("ruta.cargue.imagenes");
			String nombrePaisOrigen = params.getSinonimosDTO().getPaisOrigen();
			String rutaCargue = rutaMainImagenes + File.separatorChar + nombrePaisOrigen;
			/*
			 * toma la imagen y la guarda en rutaCargue
			 */

			boolean isCreo = false;
			try {
				isCreo = FileUtils.crearDirectorio(rutaCargue);
			} catch (Exception e) {
				Logger.getLogger(e.getLocalizedMessage());
			}

			if (isCreo) {
				long cons = counterImagenesIface.getNextSequenceId("imagenes_id");
				rutaCargue += File.separatorChar + params.getSinonimosDTO().getTermino() + cons + ".jpg";
				Path path = Paths.get(rutaCargue);
				try {
					Files.write(path, imagen);
				} catch (IOException e) {
					Logger.getLogger(e.getLocalizedMessage());
				}
			}

			sinonimosDTO.setRutasImagenes(Arrays.asList(rutaCargue));

		}
		Sinonimos sinonimos = SinonimosMapper.INSTANCE.sinonimoDTOToSinonimo(sinonimosDTO);
		sinonimosRepository.save(sinonimos);

		/**
		 * agregamos el sinonimo al contrario
		 */
		String paisOrigen = sinonimos.getPaisDestino();
		String paisDestino = sinonimos.getPaisOrigen();
		String categoria = sinonimos.getCategoria();
		String termino = sinonimos.getTermino();
		List<Palabras> valuesSinonimos = sinonimos.getSinonimos();
		List<Palabras> listTerminos = new ArrayList<>();
		if (valuesSinonimos != null && !valuesSinonimos.isEmpty()) {
			for (Palabras palabra : valuesSinonimos) {
				listTerminos.add(palabra);
			}
		}

		if (listTerminos != null && !listTerminos.isEmpty()) {
			for (Palabras palabra : listTerminos) {
				List<Sinonimos> listSinonimos = sinonimosRepository
						.findByPaisOrigenAndPaisDestinoAndTerminoAndCategoria(paisOrigen, paisDestino,
								palabra.getPalabra(), categoria);
				if (listSinonimos != null && !listSinonimos.isEmpty()) {
					List<Palabras> listTerminoComoSinonimo = new ArrayList<>();
					Palabras palabras = new Palabras();
					palabras.setPalabra(termino);
					listTerminoComoSinonimo.add(palabras);
					sinonimos.setTermino(palabra.getPalabra());

					sinonimos.setSinonimos(listTerminoComoSinonimo);
					sinonimosRepository.save(sinonimos);
				} else {
					sinonimos.setPaisDestino(paisDestino);
					sinonimos.setPaisOrigen(paisOrigen);
					List<Palabras> listTerminoComoSinonimo = new ArrayList<>();
					Palabras palabras = new Palabras();
					palabras.setPalabra(termino);
					listTerminoComoSinonimo.add(palabras);
					listTerminoComoSinonimo.add(palabras);
					sinonimos.setTermino(palabra.getPalabra());
					sinonimos.set_id(null);
					sinonimos.setSinonimos(listTerminoComoSinonimo);
					sinonimos.setDefiniciones(null);
					sinonimos.setOraciones(null);
					sinonimos.setRutasImagenes(null);
					sinonimosRepository.save(sinonimos);
				}
			}
		}
	}

	public SinonimosDTO actualizarCalificacion(SinonimosDTO sinonimosDTO) {
		Sinonimos sinonimos = SinonimosMapper.INSTANCE.sinonimoDTOToSinonimo(sinonimosDTO);
		/**
		 * actualiza los datos diferentes al sinonimo enviado
		 */
		sinonimos = sinonimosRepository.save(sinonimos);
		sinonimosDTO = SinonimosMapper.INSTANCE.sinonimosToSinonimoDTO(sinonimos);
		List<PalabrasDTO> listPalabra = sinonimosDTO.getSinonimos();
		Iterator<PalabrasDTO> iter = listPalabra.iterator();
		while(iter.hasNext()){
			PalabrasDTO palabrasDTO = iter.next();
			List<Integer> listCalificaciones = palabrasDTO.getCalificacion();
			int promedio = 0;
			if(listCalificaciones != null && !listCalificaciones.isEmpty()){
				for (Integer cal : listCalificaciones) {
					promedio += cal;
				}
				promedio /= listCalificaciones.size();
				palabrasDTO.setPromedioCalificacion(promedio);
			}
		}
		return sinonimosDTO;
	}
	

}
