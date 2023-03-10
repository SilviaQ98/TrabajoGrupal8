import {Pool} from "pg";

const express = require('express');
const bodyParser = require('body-parser')

import routes from './controllers/routes'
import { config } from "../config";

const app = express();
const port = config.serverPort;


app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
);

app.get('/', (request, response) => {
  response.json({ info: 'Node.js, Express, and Postgres API' });
});

app.get('/authors', routes.getAuthors);
app.get('/authors/:id', routes.getAuthorById);
app.post('/authors', routes.createAuthor);
app.put('/authors/:id', routes.updateAuthor);
app.delete('/authors/:id', routes.deleteAuthor);
export let pool
try {
  pool = new Pool({
    user: config.username,
    host: config.host,
    database: config.database,
    password: config.password,
    port: config.port,
  })

  pool.query('create table if not exists authors\n' +
    '(\n' +
    '    id integer generated by default as identity\n' +
    '        primary key,\n' +
    '    firstname   varchar(16) not null,\n' +
    '    lastname  varchar(128)\n' +
    ');'
  , (error, results) => {
      if (results) {
        console.log("Se creo la tabla")
        pool.query('' +
          'INSERT INTO authors (firstname, lastname)\n' +
          'SELECT \'John\', \'Doe\'\n' +
          'WHERE NOT EXISTS (SELECT 1 FROM authors);', (error, results) => {
          if (results) {
            console.log("Se creo el registro")
          }
          if (error) {
            console.log("Error al crear el registro", error)
          }
        })
      }
      if (error) {
        console.log("Error al crear la tabla", error)
      }
    })

} catch (error) {
  console.log("Error de conexion", error)
}


console.log("Conectado con estas credenciales", config)


app.listen(port, () => {
  console.log(`App running on port ${port}.`);
});
