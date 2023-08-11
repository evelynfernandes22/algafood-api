set foreign_key_checks=0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;

set foreign_key_checks=1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');
insert into cozinha (id, nome) values (3, 'Brasileira');
insert into cozinha (id, nome) values (4, 'Mexicana');
insert into cozinha (id, nome) values (5, 'Japonesa');

insert into estado (id, nome) values (1, 'Paraná');
insert into estado (id, nome) values (2, 'Santa Catarina');
insert into estado (id, nome) values (3, 'Minas Gerais');

insert into cidade (id, nome, estado_id) values (1, 'Curitiba', 1);
insert into cidade (id, nome, estado_id) values (2, 'Florianópolis', 2);
insert into cidade (id, nome, estado_id) values (3, 'Belo Horizonte', 3);

insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Xing Ling', 10, 1,true, true, 1, 86000-000, 'Rua das Flores', '500', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao) values ('Madre Mio Delivery', 9.50, 2, true, true, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao) values ('Cantina da Vovó', 3.90, 3, true, true, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao) values ('Bar do Tadeu', 2.10, 3, true, true, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao) values ('Muchachados Taqueria', 8, 4, true, true, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, ativo, aberto, data_cadastro, data_atualizacao) values('Dachô', 11, 5, true, true, utc_timestamp, utc_timestamp); 

insert into forma_pagamento (id, descricao) values (1, 'pix');
insert into forma_pagamento (id, descricao) values (2, 'cartao de credito');
insert into forma_pagamento (id, descricao) values (3, 'boleto');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2),(5, 1), (5, 3), (6, 1), (6, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pad Thai', 'Tradicional macarrao frito Tailandes', 25.00, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bistecca alla Fiorentina', 'Suculento bife grande de bisteca (T-bone), de 4 a 6 cm de altura, grelhado', 80, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porçao de frango','500g de frango à passarinho frito no capricho',21, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Taco Tradicional','com a tortilha de milho em forma de disco',25, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Prato do dia','Comidinha caseira com tempero de avó, você só encontra aqui.', 39.90, 1,3); 
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Temaki', 'Enrolado de alga em forma de cone recheado com arroz, salmão, cream cheese e cebolinha.', 18, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Peixe frito','Deliciosas iscas de tilápia frita na hora',5,1,4);

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'),(3, 'Secretária'), (4, 'Cadastrador');

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1); 

insert into usuario (id, nome, email, senha, data_cadastro) values 
(1, 'João das Couves', 'couves@email.com', '123', utc_timestamp),
(2, 'Maria das Dores', 'das.dores@email.com', '123', utc_timestamp),
(3, 'Carolina Macedo', 'carolzinha@email.com', '123', utc_timestamp),
(4, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1,1),(2,2),(3,3);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 4), (3, 4);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (1,'4d7f20ab-4ad0-4dbc-ad0b-54a56ab4b4ec', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
values (2,'6d6a14e7-a4ec-4349-a9b8-c777c3adb371', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');