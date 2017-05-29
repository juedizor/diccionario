package co.com.diccionario.mapper;

import co.com.diccionario.document.Paises;
import co.com.diccionario.dto.PaisesDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-29T12:47:56-0500",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 1.2.0.v20150514-0146, environment: Java 1.8.0_92 (Oracle Corporation)"
)
public class PaisesMapperImpl implements PaisesMapper {

    @Override
    public PaisesDTO paisesToPaisesDTO(Paises paises) {
        if ( paises == null ) {
            return null;
        }

        PaisesDTO paisesDTO = new PaisesDTO();

        paisesDTO.setId( paises.getId() );
        paisesDTO.setNombre( paises.getNombre() );

        return paisesDTO;
    }

    @Override
    public List<PaisesDTO> paisesToPaisesDTOs(List<Paises> listPaises) {
        if ( listPaises == null ) {
            return null;
        }

        List<PaisesDTO> list = new ArrayList<PaisesDTO>();
        for ( Paises paises : listPaises ) {
            list.add( paisesToPaisesDTO( paises ) );
        }

        return list;
    }
}
