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

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Xing Ling', 10, 1,1, 86000-000, 'Rua das Flores', '500', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Madre Mio Delivery', 9.50, 2, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Cantina da Vovó', 3.90, 3, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Bar do Tadeu', 2.10, 3, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Muchachados Taqueria', 8, 4, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Dachô', 11, 5, utc_timestamp, utc_timestamp); 

insert into forma_pagamento (id, descricao) values (1, 'pix');
insert into forma_pagamento (id, descricao) values (2, 'cartao de credito');
insert into forma_pagamento (id, descricao) values (3, 'boleto');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2),(5, 1), (5, 3), (6, 1), (6, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Pad Thai', 'Tradicional macarrao frito Tailandes', 25.00, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bistecca alla Fiorentina', 'Suculento bife grande de bisteca (T-bone), de 4 a 6 cm de altura, grelhado', 80, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porçao de frango','500g de frango à passarinho frito no capricho',21, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Taco Tradicional','com a tortilha de milho em forma de disco',25, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Prato do dia','Comidinha caseira com tempero de avó, você só encontra aqui.', 39.90, 1,3); 
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Temaki', 'Enrolado de alga em forma de cone recheado com arroz, salmão, cream cheese e cebolinha.', 18, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Peixe frito','Deliciosas iscas de tilápia frita na hora',5,1,4);
