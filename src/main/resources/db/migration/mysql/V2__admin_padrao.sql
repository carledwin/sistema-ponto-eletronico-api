INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social`)
VALUES (NULL, '06028939000140', CURRENT_DATE(), CURRENT_DATE(), 'Carl Edwin TI');

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '83069968015', CURRENT_DATE(), CURRENT_DATE(), 'admin@carledwinti.wordpress.com', 'Administrador do Sistema', 'ROLE_ADMIN', NULL, NULL,
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '06028939000140'));
