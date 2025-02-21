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
    (1, 'Fábio', '1978-10-15', '91999991111', 'fabio@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
    (2, 'Yan', '2001-11-09', '91999992222', 'yan@ufra.edu.br', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 1),
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

-- comitês
insert into comite
    (id, portaria, inicio, fim, coordenador)
    values
    (1, 'Portaria 2024/001 PROPED', '2024-01-01', '2024-12-31', 3),
    (2, 'Portaria 2025/001 PROPED', '2025-01-01', '2025-12-31', 3);

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
    (id, titulo, ano, numero, descricao, data_cadastro, data_inicio, data_final, situacao, tipo, coordenador, comite)
    values
    (1, 'Bolsas de Iniciação Científica', 2024, 1, '???', '2024-01-15', '2024-01-31', '2024-02-28', 'C', 'E', 3, 1),
    (2, 'Bolsas de Iniciação Científica/Tecnológica', 2025, 1, '???', '2025-01-15', '2025-01-31', '2025-02-28', 'C', 'P', 3, 2),
    (3, 'Bolsas de Iniciação Docência', 2024, 1, '???', '2024-01-15', '2025-01-31', '2025-02-28', 'C', 'E', 3, 1),
    (4, 'Bolsas de Iniciação Tecnológica', 2025, 1, '???', '2025-01-15', '2025-01-31', '2025-02-28', 'C', 'P', 3, 2);

-- inscrições
insert into inscricao
    (id, data_inscricao, protocolo, edital, pessoa)
    values
    (1, '2024-01-20', '202400100001', 1, 1),
    (2, '2024-01-21', '202400100002', 1, 3),
    (3, '2025-01-20', '202500100001', 2, 5),
    (4, '2025-01-21', '202500100002', 2, 7);

-- planos de trabalho
insert into plano_de_trabalho
    (id, data_cadastro, titulo, resumo, inscricao)
    values
    (1, '2024-01-20', 'Plano 001', 'Resumo 001', 1),
    (2, '2024-01-21', 'Plano 002', 'Resumo 002', 2),
    (3, '2025-01-20', 'Plano 003', 'Resumo 003', 3),
    (4, '2025-01-21', 'Plano 004', 'Resumo 004', 4),
    (5, '2025-01-20', 'Plano 005', 'Resumo 005', 3),
    (6, '2025-01-21', 'Plano 006', 'Resumo 006', 4);