import { pool } from "../index";


const getAuthors = async (request, response) => {
  try {
    await pool.query('SELECT * FROM authors ORDER BY id ASC', (error, results) => {
      if (error) {
        response.json(error)
      }
      response.json(results.rows);
    });
  } catch (err) {
    console.log(err.message)
  }
};

const getAuthorById = (request, response) => {
  const id = parseInt(request.params.id);
  console.log("*********************** GET ID", id)

  pool.query('SELECT * FROM authors WHERE id = $1', [id], (error, results) => {
    if (error) {
      throw error;
    }
    console.log(results.rows[0])
    response.json(results.rows[0]);
  });
};

const createAuthor = (request, response) => {
  const { firstName, lastName } = request.body;

  pool.query(
    'INSERT INTO authors (firstName, lastName) VALUES ($1, $2) RETURNING *',
    [firstName, lastName],
    (error, results) => {
      if (error) {
        throw error;
      }
      response.status(201).send(`Author added with ID: ${results.rows[0].id}`);
    }
  );
};

const updateAuthor = (request, response) => {
  const id = parseInt(request.params.id);
  const { firstName, lastName } = request.body;

  pool.query(
    'UPDATE authors SET firstName = $1, lastName = $2 WHERE id = $3',
    [firstName, lastName, id],
    (error, results) => {
      if (error) {
        throw error;
      }
      response.send(`Author modified with ID: ${id}`);
    }
  );
};

const deleteAuthor = (request, response) => {
  const id = parseInt(request.params.id);

  pool.query('DELETE FROM authors WHERE id = $1', [id], (error, results) => {
    if (error) {
      throw error;
    }
    response.send(`Author deleted with ID: ${id}`);
  });
};

export default {
  getAuthors,
  getAuthorById,
  deleteAuthor,
  updateAuthor,
  createAuthor
}
