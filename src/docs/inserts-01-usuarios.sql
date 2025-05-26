-- unidade admnistrativa
insert into unidade_administrativa
    (id, nome, sigla, tipo)
    values
    (1, 'Instituto Ciberespacial', 'ICIBE', 'I'),
    (2, 'Instituto de Ciências Agrárias', 'ICA', 'I'),
    (3, 'Instituto de Saúde e Proteção Animal', 'ISPA', 'I'),
    (4, 'Campus Paragominas', 'PGM', 'C'),
    (5, 'Campus Capitão-Poço', 'CPP', 'C'),
    (6, 'Campus Capanema', 'CAP', 'C');


-- pessoas
insert into pessoa
    (id, nome_completo, nascimento, telefone, email, senha, unidade_administrativa)
    values
    (1, 'Fábio', '1978-10-15', '91999991111', 'fabio.bezerra@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (2, 'Yan', '2001-11-09', '91999992222', 'yanblanc@discente.ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (3, 'Bruno', '1988-10-29', '91999993333', 'bruno@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (4, 'Tatiane', '1989-01-05', '91999994444', 'tatiane@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (5, 'Ivan', '1989-02-05', '91999990001', 'ivan@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (6, 'Deciola', '1989-03-05', '91999990002', 'deciola@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (7, 'Klissiomara', '1989-04-05', '91999990003', 'klissiomara@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (8, 'Orlando', '1989-05-05', '91999990004', 'orlando@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1);

-- papéis
insert into papel
    (id, nome, codigo)
    values
    (1, 'Coordenador', 'C'),
    (2, 'Gerente', 'G'),
    (3, 'Pesquisador', 'P'),
    (4, 'Bolsista', 'B'),
    (5, 'Administrador', 'A');

-- pessoas em papéis
insert into pessoa_papel
    (id, papel, pessoa)
    values
    (1, 1, 3),
    (2, 2, 4),
    (3, 3, 3),
    (4, 3, 1),
    (5, 3, 3),
    (6, 3, 5),
    (7, 3, 6),
    (8, 3, 7),
    (9, 3, 8),
    (10, 4, 2),
    (11, 5, 1),
    (12, 5, 2);