-- comitês
insert into comite
    (id, portaria, inicio, fim, coordenador)
    values
    (1, 'Portaria 2024/001 PROPED', '2024-01-01', '2024-12-31', 3),
    (2, 'Portaria 2025/001 PROPED', '2025-01-01', '2025-12-31', 3);

-- membros do comitê 2024
insert into comite_pessoa
    (id, comite, pessoa)
    values
    (1, 1, 1), -- Fábio
    (2, 1, 3), -- Bruno
    (3, 1, 5); -- Ivan

-- membros do comitê 2025
insert into comite_pessoa
(id, comite, pessoa)
values
    (4, 2, 6), -- Deciola
    (5, 2, 7), -- Klissiomara
    (6, 2, 8); -- Orlando

-- editais 2024
insert into edital
    (id, titulo, ano, numero, descricao, data_cadastro, data_inicio, data_final, situacao, tipo, coordenador, comite)
    values
    (1, 'Bolsas de Iniciação Científica', 2024, 1, '???', '2024-01-15', '2024-03-31', '2025-04-30', 'C', 'P', 3, 1),
    (2, 'Bolsas de Iniciação Docência', 2024, 2, '???', '2024-01-20', '2024-03-31', '2025-05-31', 'C', 'E', 3, 1);

-- editais 2025
insert into edital
(id, titulo, ano, numero, descricao, data_cadastro, data_inicio, data_final, situacao, tipo, coordenador, comite)
values
    (3, 'Bolsas de Iniciação Científica e Tecnológica', 2025, 1, '???', '2025-01-25', '2025-04-30', '2025-10-30', 'C', 'P', 3, 2),
    (4, 'Bolsas de Iniciação Tecnológica', 2025, 2, '???', '2025-01-30', '2025-05-02', '2025-08-20', 'C', 'D', 3, 2);

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