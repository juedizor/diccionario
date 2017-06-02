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

