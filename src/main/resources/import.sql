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

insert into municipio (nome, id_estado) values( 'Palmas', 1);
insert into municipio (nome, id_estado) values( 'Goiânia', 2);
insert into municipio (nome, id_estado) values( 'São Paulo', 3);
insert into municipio (nome, id_estado) values( 'Salvador', 4);