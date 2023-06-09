create table estado(
	id bigint auto_increment not null,
    nome varchar(80),
    
    primary key  (id)
)engine=InnoDB default charset=utf8;

insert into algafood.estado (nome) select distinct nome_estado from cidade;

alter table cidade add column estado_id bigint not null;

UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);

alter table cidade add constraint fk_cidade_estado foreign key (estado_id) references estado (id);

alter table cidade drop column nome_estado;

alter table cidade change nome_cidade nome varchar(80) not null;