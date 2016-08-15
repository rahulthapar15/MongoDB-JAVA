**Create Collection**

`            MongoCollection<Document> collection = database.getCollection("collection_name");
`

**Create Documents**
`Document document_name Document();`

**Inserting Data**
`document_name.append("_id",1)`
                    `.append("field_1","value_1")`
                    `.append("field_2","value_2")`
                    `.append("field_3","value_3");`


**Adding Document to Collection**
` collection.insertOne( document_name );`

**Update Document**
`collection.updateOne(new Document("_id", 1),`
`                    new Document("$set", new Document("pokemonName", "Raichu")
`
`                            .append("HP", "60")
`
`                            .append("attack", "90")
`

### Delete Many using constraint/filters
`            collection.deleteMany(Filters.gte("_id", 3));
`


