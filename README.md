# **Book Manager API**
Spring Boot - Rest API Book Manager

##  **Book Entity**

#### **Endpoints**
- BaseURL: /api/v1/books
- POST: create()
- GET: getAll()
- GET /{name}: getByName()
- PUT /{id}: update()
- PATCH ?book= & author=: addAuthorToABook()
- PATCH ?book= & category=: addCategoryToABook()
- DELETE/{id}: inactivate() --> Will be implemented
- DELETE /delete/{id}: delete()

#### **Model**

```json
{
    "id" : 1,
    "title": "Licoes Preliminares De Direito",
    "author": "Miguel Reale",
    "year": 1984,
    "category": "Direito",
    "pages": 320,
    "language": "português-br",
    "image":"http://google.com/LicoesPreliminaresDeDireito.png",
    "price": 22.90,
    "is-available": true
}
```

---

## **Author Entity**

#### **Endpoints**
- BaseURL: /api/v1/authors
- POST: create()
- GET: getAll()
- GET /name={name}: getByName()
- PUT /{id}: update()
- DELETE /{id}: delete()

#### **Model**

```json
{
    "id": 1,
    "name": "James Clear",
    "about" : "description"
}
```

---

## **Category Entity**

#### **Endpoints**
- BaseURL: /api/v1/categories
- POST: create()
- GET: getAll()
- GET /{id}: getByName()
- GET /books/category?name=: getBooksByCategory()
- PUT /{id}: update()
- DELETE /{id}: delete()

#### **Model**

```json
{
    "id": 1,
    "name": "Romance"
}
```

---

## **Object-Relational Mapping**

### **Book -> Author:**

Relation _many to many_, that is one book can have many authors and one author can have many books.

```json
{
    "id_book": 1,
    "id_author": 1
}
```

### **Book -> Category:**

Relation _many to many_, that is one book can have many categories and one category can have many books.

```json
{
    "id_book": 1,
    "id_category": 1
}
```
