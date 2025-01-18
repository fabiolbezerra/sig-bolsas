-- pessoas
insert into pessoa
    (id, nome_completo, nascimento, telefone, email)
    values
    (1, 'Fábio', '1978-10-15', '91999991111', 'fabio@ufra.edu.br'),
    (2, 'Yan', '2001-11-09', '91999992222', 'yan@ufra.edu.br'),
    (3, 'Bruno', '1988-10-29', '91999993333', 'bruno@ufra.edu.br'),
    (4, 'Tatiane', '1989-01-05', '91999994444', 'tatiane@ufra.edu.br'),
    (5, 'Ivan', '1989-02-05', '91999990001', 'ivan@ufra.edu.br'),
    (6, 'Deciola', '1989-03-05', '91999990002', 'deciola@ufra.edu.br'),
    (7, 'Klissiomara', '1989-04-05', '91999990003', 'klissiomara@ufra.edu.br'),
    (8, 'Orlando', '1989-05-05', '91999990004', 'orlando@ufra.edu.br');

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

-- comitês
insert into comite
    (id, inicio, fim, coordenador)
    values
    (1, '2024-01-01', '2024-12-31', 3),
    (2, '2025-01-01', '2025-12-31', 3);

-- membros dos comitês
insert into comite_pessoa
    (id, comite, pessoa)
    values
    (1, 1, 1),
    (2, 1, 3),
    (3, 1, 5),
    (4, 2, 6),
    (5, 2, 7),
    (6, 2, 8);

-- editais
insert into edital
    (id, titulo, ano, numero, descricao, data_cadastro, data_inicio, data_final, situacao, coordenador, comite)
    values
    (1, 'Bolsas de Iniciação Científica', 2024, 1, '???', '2024-01-15', '2024-01-31', '2024-02-28', 'C', 3, 1),
    (2, 'Bolsas de Iniciação Científica/Tecnológica', 2025, 1, '???', '2025-01-15', '2025-01-31', '2025-02-28', 'C', 3, 2);