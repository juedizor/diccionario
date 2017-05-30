var ciudad = {
	_id: 1, 
	nombre: 'BOGOTA'
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 2, 
	nombre: 'ZIPAQUIRA'
};

db.ciudad.insert(ciudad);


var departamento = {
	_id: 1, 
	nombre: 'CUNDINAMARCA', 
	ciudades: [1, 2]
};

db.departamento.insert(departamento);


var ciudad = {
	_id: 3, 
	nombre: 'MONTERIA'
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 4, 
	nombre: 'CERETE'
};

db.ciudad.insert(ciudad);

var departamento = {
	_id: 2, 
	nombre: 'CORDOBA', 
	ciudades: [3, 4]
};

db.departamento.insert(departamento);


var ciudad = {
	_id: 5, 
	nombre: 'RIVADAVIA'
};

db.ciudad.insert(ciudad);

var ciudad = {
	_id: 6, 
	nombre: 'RIVADAVIA 1'
};

db.ciudad.insert(ciudad);

var departamento = {
	_id: 3, 
	nombre: 'SAN JUAN', 
	ciudades: [5, 6]
};

db.departamento.insert(departamento);


var paises = {
	_id: 1, 
	nombre: 'COLOMBIA', 
	departamentos: [1, 2]
};

db.paises.insert(paises);

var paises = {
	_id: 2, 
	nombre: 'ARGENTINA', 
	departamentos: [3]
};

db.paises.insert(paises);


var categoria = {
	nombre: ['ROPA', 'COMIDAS', 'TRANSPORTE', 'ANIMALES']
}


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

