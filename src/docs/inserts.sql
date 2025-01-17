-- pessoas
insert into pessoa
    (id, nome_completo, nascimento, telefone, email)
    values
    (1, 'Fábio', '1978-09-05', '91999991111', 'fabio@ufra.edu.br'),
    (2, 'Yan', '2001-08-29', '91999992222', 'yan@ufra.edu.br'),
    (3, 'Bruno', '1988-10-29', '91999993333', 'bruno@ufra.edu.br'),
    (4, 'Tatiane', '1989-11-05', '91999994444', 'tatiane@ufra.edu.br');

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
    (5, 4, 2),
    (6, 5, 1),
    (7, 5, 2);

