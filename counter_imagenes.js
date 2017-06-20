db.counters_imagenes.insert(
   {
      _id: "imagenes_id",
      seq: 0
   }
);

function getNextSequence(name) {
   var ret = db.counters.findAndModify(
          {
            query: { _id: name },
            update: { $inc: { seq: 1 } },
            new: true
          }
   );

   return ret.seq;
};