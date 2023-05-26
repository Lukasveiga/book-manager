INSERT INTO tb_book(title, pages, book_year, language, image) VALUES ('Java Efetivo', 432, 2019,'português-br', 'https://m.media-amazon.com/images/I/81+PX6Q7Q2L.jpg');
INSERT INTO tb_author(name, about) VALUES('Joshua Bloch', 'Joshua J. Bloch, nascido em 28 de agosto de 1961, é um engenheiro de software, anteriormente funcionário da Google, atualmente um autor de temas ligado a tecnologia. Liderou a concepção e implementação de inúmeras funcionalidades da plataforma Java, incluindo o Java Collections, o pacote java.math e seus mecanismos.');
INSERT INTO book_author(id_book, id_author) VALUES ((SELECT id FROM tb_book WHERE title = 'Java Efetivo'), (SELECT id FROM tb_author WHERE name = 'Joshua Bloch'));
INSERT INTO tb_category(name) VALUES('Tecnologia');
INSERT INTO book_category(id_book, id_category) VALUES ((SELECT id FROM tb_book WHERE title = 'Java Efetivo'), (SELECT id FROM tb_category WHERE name = 'Tecnologia'));

