-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
 --insert into product ('nome', 'descrição') values(nextval('hibernate_sequence'), 'field-1');





insert into Usuario (nome, email,senha,cpf) values('tifu','tifu@email.com', '123', '123.321.23-08');
insert into Usuario (nome, email,senha,cpf) values('meive','meive@email.com', '321', '123.321.23-04');
insert into Usuario (nome, email,senha,cpf) values('Livia','livia@email.com', '2606', '123.331.23-08');
insert into Usuario (nome, email,senha,cpf) values('Ledrus','ledrus@email.com', '234', '123.333.23-08');


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

