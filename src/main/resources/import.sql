-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
 --insert into product ('nome', 'descrição') values(nextval('hibernate_sequence'), 'field-1');



insert into pessoa (nome) values('Immanuel Kant');
insert into pessoa (nome) values('Martinho Lutero');

insert into pessoafisica(id, cpf, sexo) values (1, '111.111.111-11', 1);
insert into pessoafisica(id, cpf) values (2, '222.222.222-22');

insert into usuario (login, senha, id_pessoa_fisica) values('ImmanuelKant', 'GU5e4piE62AOdQr2e5/r0In9A7OJ89TjYO0SxgzZysU5HyEtOo2uzOiH3xUqXEQXEWXQCTkB/LQeiS4m0bFs7w==', 1);
insert into usuario (login, senha, id_pessoa_fisica) values('MartinhoLutero', '2jqHB2Uf9imuz2oRVlzQCEMTCOoHPgbnPCwXCm100JmUzMNhlZFMjcXoeWp9T91TTCruG2sL5JNYRvt6wtw2Ew==', 2);
insert into perfis (id_usuario, perfil) values (1, 'Admin');
insert into perfis (id_usuario, perfil) values (1, 'User');
insert into perfis (id_usuario, perfil) values (2, 'User');

 
insert into Estado (nome, sigla) values ('Tocantins','TO');
insert into Estado (nome, sigla) values ('Goiás','GO');
insert into Estado (nome, sigla) values ('São Paulo','SP');
insert into Estado (nome, sigla) values ('Bahia','BA');

insert into Municipio (nome, id_estado) values( 'Palmas', 1);
insert into Municipio (nome, id_estado) values( 'Goiânia', 2);
insert into Municipio (nome, id_estado) values( 'São Paulo', 3);
insert into Municipio (nome, id_estado) values( 'Salvador', 4);

insert into Endereco (principal, logradouro, bairro, numero, complemento, cep, id_municipio) values(false,'lograteste','bairroteste','123','completeste','44543-232','1');
insert into Endereco (principal, logradouro, bairro, numero, complemento, cep, id_municipio) values(true,'lograteste','bairroteste','123','completeste','44543-232','2');
insert into Endereco (principal, logradouro, bairro, numero, complemento, cep, id_municipio) values(false,'lograteste','bairroteste','123','completeste','44543-232','3');
insert into Endereco (principal, logradouro, bairro, numero, complemento, cep, id_municipio) values(true,'lograteste','bairroteste','123','completeste','44543-232','4');

insert into Telefone(codigoArea, numero) values('62','987433723');
insert into Telefone(codigoArea, numero) values('98','789234109');
insert into Telefone(codigoArea, numero) values('63','983617342');
insert into Telefone(codigoArea, numero) values('74','934812347');
insert into Telefone(codigoArea, numero) values('11','923741342');


INSERT INTO alergenico(descricao) VALUES ('Láctose');
INSERT INTO alergenico(descricao) VALUES ('Glúten');
INSERT INTO alergenico(descricao) VALUES ('Amendoim');
INSERT INTO alergenico(descricao) VALUES ('Avelã');

INSERT INTO ingrediente(nome) VALUES ('Farinha de Trigo');
INSERT INTO ingrediente(nome) VALUES ('Leite');
INSERT INTO ingrediente(nome) VALUES ('Chocolate');
INSERT INTO ingrediente(nome) VALUES ('Queijo');

INSERT INTO public.produto( descricao, estoque, nome, preco) VALUES ('Bolo recheado com Chocolate trufado', 12, 'Bolo bom', 123);
INSERT INTO public.produto( descricao, estoque, nome, preco) VALUES ('Torta de limão taiti', 11, 'Torta limo', 50);
INSERT INTO public.produto( descricao, estoque, nome, preco) VALUES ('Biscoito com cobertura e gota de chocolate',54, 'Biscoito bom', 8);
INSERT INTO public.produto( descricao, estoque, nome, preco) VALUES ('Rocambole de nutella', 232, 'Rocambella', 12312123123);

INSERT INTO confeitaria(categoria, peso, id, id_alergenico) VALUES (1, 2.4, 1, 3);
INSERT INTO confeitaria(categoria, peso, id, id_alergenico) VALUES (2, 4.5, 2, 4);
INSERT INTO confeitaria(categoria, peso, id, id_alergenico) VALUES (3, 2.4, 3, 1);
INSERT INTO confeitaria(categoria, peso, id, id_alergenico) VALUES (4, 2.4, 4, 2);

INSERT INTO public.lista_desejo(
	id_produto, id_usuario)
	VALUES (2, 1);
  INSERT INTO public.lista_desejo(
	id_produto, id_usuario)
	VALUES (1, 1);

 
