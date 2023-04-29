-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
 --insert into product ('nome', 'descrição') values(nextval('hibernate_sequence'), 'field-1');








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

insert into Usuario (nome, email,senha,cpf,id_endereco, id_telefone) values('tifu','tifu@email.com', '123', '123.321.23-08',1,4);
insert into Usuario (nome, email,senha,cpf,id_endereco, id_telefone) values('meive','meive@email.com', '321', '123.321.23-04',2,3);
insert into Usuario (nome, email,senha,cpf,id_endereco, id_telefone) values('Livia','livia@email.com', '2606', '123.331.23-08',3,2);
insert into Usuario (nome, email,senha,cpf,id_endereco, id_telefone) values('Ledrus','ledrus@email.com', '234', '123.333.23-08',4,1);

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





