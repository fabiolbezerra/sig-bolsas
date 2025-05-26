-- formulário
insert into formulario
    (id, nome, data_criacao)
    values
    (1, 'Inscrição PROICT', '2025-05-25');

-- vinculando o formulário criado aos editais
-- update edital set formulario = 1 where id = 1;
-- update edital set formulario = 1 where id = 2;
-- update edital set formulario = 1 where id = 3;
-- update edital set formulario = 1 where id = 4;

-- pergunta
insert into pergunta
    (id, texto, pontuacao, tipo, formulario)
    values
    (1, 'NOME DO GRUPO DE PESQUISA, certificado no DGP/CNPq.', 0, 'TEXTO', 1),
    (2, 'Vínculo institucional', 0, 'RESPOSTA_FECHADA', 1),
    (3, 'Trabalhos COMPLETO publicados em anais de eventos científicos', 5, 'INTEIRO', 1),
    (4, 'Autoria de Livro  e/ou e-book  técnico-científico especializado', 90, 'INTEIRO', 1),
    (5, 'Software, jogos eletrônicos', 30, 'INTEIRO', 1),
    (6, 'Atua como professor(a) ativo(a) do projeto Forma Pará', 0, 'SIM_NAO', 1);

-- opção de perguntas fechadas
insert into opcao_pergunta
    (id, resposta, pergunta)
    values
    (1, 'Professor(a) visitante', 2),
    (2, 'Professor(a) colaborador(a) aposentado(a)', 2),
    (3, 'Pós-Doc com atuação na Pós-graduação Stricto sensu', 2),
    (4, 'Docente Titular', 2),
    (5, 'Docente Associado', 2),
    (6, 'Docente Adjunto', 2),
    (7, 'Docente Assistente', 2),
    (8, 'Técnico Administrativo em Educação (nível superior)', 2),
    (9, 'Outro', 2);