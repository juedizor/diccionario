var paises = {
	_id: 1, 
	nombre: 'COLOMBIA'
};

db.paises.insert(paises);

var departamento = {
	_id: 1, 
	nombre: 'CUNDINAMARCA', 
	pais: 1
};

db.departamento.insert(departamento);

var departamento = {
	_id: 2, 
	nombre: 'CORDOBA', 
	pais: 1
};

db.departamento.insert(departamento);

var paises = {
	_id: 2, 
	nombre: 'ARGENTINA'
};

db.paises.insert(paises);

var departamento = {
	_id: 3, 
	nombre: 'SAN JUAN',
	pais: 2
};

db.departamento.insert(departamento);


var ciudad = {
	_id: 1, 
	nombre: 'BOGOTA', 
	departamento: 1
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 2, 
	nombre: 'ZIPAQUIRA', 
	departamento: 1
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 3, 
	nombre: 'MONTERIA', 
	departamento: 2
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 4, 
	nombre: 'CERETE', 
	departamento: 2
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 5, 
	nombre: 'RIVADAVIA', 
	departamento: 3
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 6, 
	nombre: 'RIVADAVIA 1', 
	departamento: 3
};

db.ciudad.insert(ciudad);


/**
ingresamos los paises
**/

var paises = {
	_id: 3, 
	nombre: 'BRASIL'
};

db.paises.insert(paises);


var paises = {
	_id: 4, 
	nombre: 'CHILE'
};

db.paises.insert(paises);


var paises = {
	_id: 5, 
	nombre: 'BOLIVIA'
};

db.paises.insert(paises);

var paises = {
	_id: 6, 
	nombre: 'COSTA RICA'
};

db.paises.insert(paises);



var paises = {
	_id: 7, 
	nombre: 'CUBA'
};

db.paises.insert(paises);


var paises = {
	_id: 8, 
	nombre: 'ECUADOR'
};

db.paises.insert(paises);


var paises = {
	_id: 9, 
	nombre: 'EL SALVADOR'
};

db.paises.insert(paises);


var paises = {
	_id: 10, 
	nombre: 'GUATEMALA'
};

db.paises.insert(paises);


var paises = {
	_id: 11, 
	nombre: 'GUYANA'
};

db.paises.insert(paises);


var paises = {
	_id: 12, 
	nombre: 'HONDURAS'
};

db.paises.insert(paises);


var paises = {
	_id: 13, 
	nombre: 'MEXICO'
};

db.paises.insert(paises);


var paises = {
	_id: 14, 
	nombre: 'NICARAGUA'
};

db.paises.insert(paises);


var paises = {
	_id: 15, 
	nombre: 'PANAMA'
};

db.paises.insert(paises);


var paises = {
	_id: 16, 
	nombre: 'PARAGUAY'
};

db.paises.insert(paises);

var paises = {
	_id: 17, 
	nombre: 'PERU'
};

db.paises.insert(paises);


var paises = {
	_id: 18, 
	nombre: 'PUERTO RICO'
};

db.paises.insert(paises);

var paises = {
	_id: 19, 
	nombre: 'REPUBLICA DOMINICANA'
};

db.paises.insert(paises);

var paises = {
	_id: 20, 
	nombre: 'URUGUAY'
};

db.paises.insert(paises);

var paises = {
	_id: 21, 
	nombre: 'VENEZUELA'
};

db.paises.insert(paises);















var categoria = {
	nombre: 'ROPA'
}

db.categoria.insert(categoria);

var categoria = {
	nombre: 'COMIDAS'
}

db.categoria.insert(categoria);

var categoria = {
	nombre: 'TRANSPORTE'
}

db.categoria.insert(categoria);

var categoria = {
	nombre: 'ANIMALES'
}

db.categoria.insert(categoria);

var categoria = {
	nombre: 'FRUTA'
}

db.categoria.insert(categoria);

var palabra = {
	pais_origen: 'ARGENTINA', 
	pais_destino: 'COLOMBIA', 
	termino: 'POLERA',
	categoria: 'ROPA',
	sinonimos: ['BUZO']
};

db.sinonimos.insert(palabra);

var palabra = {
	pais_origen: 'ARGENTINA', 
	pais_destino: 'COLOMBIA', 
	termino: 'CAMPERA',
	categoria: 'ROPA',
	sinonimos: ['CHAQUETA']
};

db.sinonimos.insert(palabra);

var palabra = {
	pais_origen: 'COLOMBIA', 
	pais_destino: 'ARGENTINA', 
	termino: 'PARCE',
	sinonimos: ['HERMANO', 'BOLUDO'], 
	oraciones: ['BOLUDO NOS VEMOS MAS TARDE', 'BOLUDO PASAME EL FERNET']
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'COLOMBIA', 
	pais_destino: 'ARGENTINA',
	termino: 'PEREZA',
	sinonimos: ['PAJA'], 
	oraciones: ['QUE PAJA QUE TENGO']
};

db.sinonimos.insert(palabra);

var palabra = {
	pais_origen: 'COLOMBIA', 
	departamento_origen: 'CORDOBA', 
	pais_destino: 'COLOMBIA',
	departamento_destino: 'CUNDINAMARCA', 
	termino: 'VAINA',
	sinonimos: ['COSA', 'OBJETO'], 
	oraciones: ['PASAME LA VAINA ESA', 'PASAME LA LLAVE QUE ESTA EN LA MESA']
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'COLOMBIA', 
	departamento_origen: 'CORDOBA', 
	pais_destino: 'COLOMBIA',
	departamento_destino: 'CUNDINAMARCA', 
	termino: 'AJA',
	sinonimos: ['SI', 'CORRECTO']
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'COLOMBIA', 
	pais_destino: 'ARGENTINA',
	termino: 'PIÑA',
	categoria: 'FRUTA',
	sinonimos: ['ANANA']
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'ARGENTINA', 
	pais_destino: 'COLOMBIA',
	termino: 'ANANA',
	categoria: 'FRUTA',
	sinonimos: ['PIÑA']
};

db.sinonimos.insert(palabra);

