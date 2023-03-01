
const booksdb = db.getSiblingDB("distribuida")
booksdb.insertOne(
    {
        title: "The Lord of the Rings",
        isbn: "978-05",
        author: 1,
        price: 10.10
    }
)
