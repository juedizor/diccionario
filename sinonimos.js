var palabra = {
	pais_origen: 'ARGENTINA', 
	pais_destino: 'COLOMBIA', 
	termino: 'POLERA',
	categoria: 'ROPA',
	sinonimos:[
		{palabra: 'BUZO', calificacion: [1,2,5,4]}
	]
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'COLOMBIA', 
	pais_destino: 'ARGENTINA', 
	termino: 'BUZO',
	categoria: 'ROPA',
	sinonimos:[
		{palabra: 'POLERA', calificacion: [1,2,5,4]}
	]
};

db.sinonimos.insert(palabra);


var palabra = {
	pais_origen: 'ARGENTINA', 
	pais_destino: 'COLOMBIA', 
	termino: 'CAMPERA',
	categoria: 'ROPA',
	sinonimos:[
		{palabra: 'CHAQUETA', calificacion: [1,2,5,4]}
	]
};

db.sinonimos.insert(palabra);

var palabra = {
	pais_origen: 'COLOMBIA', 
	pais_destino: 'ARGENTINA', 
	termino: 'PARCE',
	sinonimos:[
		{palabra: 'HERMANO', calificacion: [1,2,5,4]}, 
		{palabra: 'BOLUDO', calificacion: [1,2,5,3]}
	],
	oraciones:[
		{oracion: 'BOLUDO NOS VEMOS MAS TARDE', calificacion: [1,2,5,4]}, 
		{oracion: 'BOLUDO PASAME EL FERNET', calificacion: [1,2,5,3]}
	]
};

db.sinonimos.insert(palabra);