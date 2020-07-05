CREATE TABLE IF NOT EXISTS 
	tipo_usuario 
	(
		tipo_usuario_id bigint NOT NULL AUTO_INCREMENT, 
		nome_tipo varchar(255) NOT NULL, 
		PRIMARY KEY (tipo_usuario_id), 
		CONSTRAINT UKfwnvmiyba2pd8j291f50bkfl8 UNIQUE (nome_tipo)
	)
	ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;

INSERT IGNORE INTO tipo_usuario VALUES (default,'STUDIO');
INSERT IGNORE INTO tipo_usuario VALUES (default,'TATUADOR');

CREATE TABLE IF NOT EXISTS 
	usuario (
		usuario_id bigint NOT NULL AUTO_INCREMENT, 
		acessos bigint NOT NULL, 
		ativo bit NOT NULL, 
		comissao_credito decimal(19,2) NOT NULL, 
		comissao_credito_acima_de decimal(19,2) NOT NULL, 
		comissao_debito decimal(19,2) NOT NULL, 
		comissao_debito_acima_de decimal(19,2) NOT NULL, 
		comissao_dinheiro decimal(19,2) NOT NULL, 
		comissao_dinheiro_acima_de decimal(19,2) NOT NULL,
		comissao_produto decimal(19,2) NOT NULL, 
		email varchar(255) NOT NULL, 
		login varchar(255) NOT NULL, 
		nome varchar(255) NOT NULL, 
		quantidade_parcelas int NOT NULL, 
		senha varchar(255) NOT NULL, 
		tipo_usuario_id varchar(255) NOT NULL, 
		ultimo_acesso datetime, 
		valor_comissao_acima_de decimal(19,2) NOT NULL, 
		PRIMARY KEY (usuario_id), 
		CONSTRAINT UKpm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login), 
		CONSTRAINT UK5171l57faosmj8myawaucatdw UNIQUE (email)
	) ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;
	
INSERT IGNORE INTO usuario (
	usuario_id, 
	acessos, 
	ativo, 
	comissao_credito, 
	comissao_credito_acima_de, 
	comissao_debito, 
	comissao_debito_acima_de, 
	comissao_dinheiro, 
	comissao_dinheiro_acima_de, 
	comissao_produto,
	email, 
	login, 
	nome, 
	quantidade_parcelas, 
	senha, 
	tipo_usuario_id, 
	valor_comissao_acima_de
) VALUES (
	default, 
	0, 
	true, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5,
	0.1,
	'admin@admin.com.br',
	'admin', 
	'Administrador',
	6, 
	'$2a$10$gFWfhYIgeIzeXvQckDbZFuo8dyFrEqeH1WtYczSL0mzK4zMdMHgfy',
	1, 
	300.00
);

INSERT IGNORE INTO usuario (
	usuario_id, 
	acessos, 
	ativo, 
	comissao_credito, 
	comissao_credito_acima_de, 
	comissao_debito, 
	comissao_debito_acima_de, 
	comissao_dinheiro, 
	comissao_dinheiro_acima_de, 
	comissao_produto,
	email, 
	login, 
	nome, 
	quantidade_parcelas, 
	senha, 
	tipo_usuario_id, 
	valor_comissao_acima_de
) VALUES (
	default, 
	0, 
	true, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5,
	0.1,
	'master@master.com.br',
	'master', 
	'Master',
	6, 
	'$2a$10$CoTCGbTAtJVUTixC6HPSKuoerxDrRbO8/kQnmPz5If4T4EyvrYcn.',
	1, 
	300.00
);
 
INSERT IGNORE INTO usuario (
	usuario_id, 
	acessos, 
	ativo, 
	comissao_credito, 
	comissao_credito_acima_de, 
	comissao_debito, 
	comissao_debito_acima_de, 
	comissao_dinheiro, 
	comissao_dinheiro_acima_de, 
	comissao_produto,
	email, 
	login, 
	nome, 
	quantidade_parcelas, 
	senha, 
	tipo_usuario_id, 
	valor_comissao_acima_de
) VALUES (
	default, 
	0, 
	true, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5,
	0.1,
	'drtattoobh@gmail.com',
	'muzzi', 
	'Rogerio Muzzi',
	6, 
	'$2a$10$W5Z9N6b.YhlHcv0EJXtVa.0ESsFRZTr7kjrrlOP5KdyhAa3QYfLby',
	2, 
	300.00
);
 
INSERT IGNORE INTO usuario (
	usuario_id, 
	acessos, 
	ativo, 
	comissao_credito, 
	comissao_credito_acima_de, 
	comissao_debito, 
	comissao_debito_acima_de, 
	comissao_dinheiro, 
	comissao_dinheiro_acima_de, 
	comissao_produto,
	email, 
	login, 
	nome, 
	quantidade_parcelas, 
	senha, 
	tipo_usuario_id, 
	valor_comissao_acima_de
) VALUES (
	default, 
	0, 
	true, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5,
	0.1,
	'carinaribcardoso@gmail.com',
	'dot', 
	'Carina Riberio Cardoso',
	6, 
	'$2a$10$W5Z9N6b.YhlHcv0EJXtVa.0ESsFRZTr7kjrrlOP5KdyhAa3QYfLby',
	2, 
	300.00
);
 
INSERT IGNORE INTO usuario (
	usuario_id, 
	acessos, 
	ativo, 
	comissao_credito, 
	comissao_credito_acima_de, 
	comissao_debito, 
	comissao_debito_acima_de, 
	comissao_dinheiro, 
	comissao_dinheiro_acima_de, 
	comissao_produto,
	email, 
	login, 
	nome, 
	quantidade_parcelas, 
	senha, 
	tipo_usuario_id, 
	valor_comissao_acima_de
) VALUES (
	default, 
	0, 
	true, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5, 
	0.5,
	0.1,
	'thiagolte@hotmail.com',
	'thiago', 
	'Thiago Leite',
	6, 
	'$2a$10$W5Z9N6b.YhlHcv0EJXtVa.0ESsFRZTr7kjrrlOP5KdyhAa3QYfLby',
	2, 
	300.00
);
 	
CREATE TABLE IF NOT EXISTS 
	produto 
	(
		produto_id bigint NOT NULL AUTO_INCREMENT, 
		ativo bit NOT NULL, 
		data_cadastro datetime NOT NULL,
		data_alteracao datetime, 
		descricao varchar(255) NOT NULL, 
		observacao varchar(255) NOT NULL, 
		preco decimal(19,2) NOT NULL, 
		PRIMARY KEY (produto_id)
	) 
	ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;

INSERT IGNORE INTO produto (produto_id,ativo,data_cadastro,data_alteracao,descricao,observacao,preco) VALUES (default,true,now(),now(),'Jewel','Produto Importado',500.00);
INSERT IGNORE INTO produto (produto_id,ativo,data_cadastro,data_alteracao,descricao,observacao,preco) VALUES (default,true,now(),now(),'Piercing 6mm','Produto Importado',200.00);
INSERT IGNORE INTO produto (produto_id,ativo,data_cadastro,data_alteracao,descricao,observacao,preco) VALUES (default,true,now(),now(),'Piercing 8mm','Produto Importado',380.00);
INSERT IGNORE INTO produto (produto_id,ativo,data_cadastro,data_alteracao,descricao,observacao,preco) VALUES (default,true,now(),now(),'Piercing 12mm','Produto Importado',400.00);
	
CREATE TABLE IF NOT EXISTS 
	servico 
	(
		servico_id bigint NOT NULL AUTO_INCREMENT,
		ativo bit NOT NULL,  
		data_cadastro datetime NOT NULL,
		data_alteracao datetime, 
		descricao varchar(255) NOT NULL, 
		observacao varchar(255) NOT NULL, 
		PRIMARY KEY (servico_id)
	) 
	ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;

INSERT IGNORE INTO servico (servico_id,ativo,data_cadastro,data_alteracao,descricao,observacao) VALUES (default,true,now(),now(),'Tatuagem Permanente','Feita à agulha');
INSERT IGNORE INTO servico (servico_id,ativo,data_cadastro,data_alteracao,descricao,observacao) VALUES (default,true,now(),now(),'Tatuagem Henna','Não permanente');
INSERT IGNORE INTO servico (servico_id,ativo,data_cadastro,data_alteracao,descricao,observacao) VALUES (default,true,now(),now(),'Furo Piercing','Perfuração');
INSERT IGNORE INTO servico (servico_id,ativo,data_cadastro,data_alteracao,descricao,observacao) VALUES (default,true,now(),now(),'Retoque','Retoque de Tatuagem');

CREATE TABLE
    endereco
    (
        endereco_id INT NOT NULL AUTO_INCREMENT,
        bairro VARCHAR(255) NOT NULL,
        cep VARCHAR(255) NOT NULL,
        cidade VARCHAR(255) NOT NULL,
        estado VARCHAR(255) NOT NULL,
        pais VARCHAR(255) NOT NULL,
        rua VARCHAR(255) NOT NULL,
        PRIMARY KEY (endereco_id)
    )
    ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;

INSERT IGNORE INTO endereco(endereco_id,bairro,cep,cidade,estado,pais,rua) VALUES (default,'Camargos','31564-844','BELO HORIZONTE','MINAS GERAIS','BRASIL','Rua Infinita');

CREATE TABLE IF NOT EXISTS 
	cliente 
	(	
		cliente_id bigint NOT NULL AUTO_INCREMENT, 
		autoriza_fotos bit, 
		celular varchar(255) NOT NULL, 
		cpf varchar(255) NOT NULL, 
		data_cadastro datetime NOT NULL, 
		data_modificacao datetime, 
		data_nascimento datetime NOT NULL, 
		email varchar(255) NOT NULL, 
		instagran varchar(255), 
		nome varchar(255) NOT NULL, 
		possui_alergias bit, 
		possui_diabetes bit, 
		possui_hepatite bit, 
		telefone varchar(255), 
		tipo_sangue varchar(255), 
		toma_anticoagulante bit, 
		endereco_id int, 
		usuario_id bigint, 
		PRIMARY KEY (cliente_id), 
		CONSTRAINT UKndm1apkui2hg5y7ds5m48d8ut UNIQUE (nome),  
		INDEX FK64nr9yt889by5lufr1boo5i4s (endereco_id), 
		INDEX FKc3u631ocxdrtm3ccpme0kjlmu (usuario_id)
	) 
	ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;
	
INSERT IGNORE INTO cliente(cliente_id,autoriza_fotos,celular,cpf,data_cadastro,data_modificacao,data_nascimento,email,instagran,nome,possui_alergias,possui_diabetes,
possui_hepatite,telefone,tipo_sangue,toma_anticoagulante,endereco_id,usuario_id) VALUES (default,true,'(31) 99999-9999','782.367.367-33',CURRENT_DATE(),CURRENT_DATE(),
'1992-02-25 00:00:00','tester@cliente.br','@testerinst','Tester Tattools',true,false,false,'(31) 3444-8844','O+',false,1,1);

CREATE TABLE IF NOT EXISTS
    parametros_sistema
    (
        parametro_id bigint NOT NULL AUTO_INCREMENT,
        chave VARCHAR(255) NOT NULL,
        descricao VARCHAR(255) NOT NULL,
        valor VARCHAR(255) NOT NULL,
        PRIMARY KEY (parametro_id),
        CONSTRAINT UK4iy10k64t20l3dvn1vr783y1p UNIQUE (chave)
    )
    ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;

INSERT IGNORE INTO parametros_sistema(parametro_id,chave,descricao,valor) VALUES (default,'email_de_envio','Buscar parametro para enviar e-mail','tattoolsadm@gmail.com');
INSERT IGNORE INTO parametros_sistema(parametro_id,chave,descricao,valor) VALUES (default,'enviar_email_cadastro','Enviar E-mail ao efetuar cadastro','1');
INSERT IGNORE INTO parametros_sistema(parametro_id,chave,descricao,valor) VALUES (default,'admin_gera_os','Habita STUDIO gerar OS','0');
INSERT IGNORE INTO parametros_sistema(parametro_id,chave,descricao,valor) VALUES (default,'black_box_report','Reportar atividades do sistema','1');

CREATE TABLE IF NOT EXISTS
    parametros_email
    (
        parametro_email_id bigint NOT NULL AUTO_INCREMENT,
        config_set VARCHAR(255) NOT NULL,
        email_from VARCHAR(255) NOT NULL,
        email_from_name VARCHAR(255) NOT NULL,
        email_port INT NOT NULL,
        email_smtp_host VARCHAR(255) NOT NULL,
        email_smtp_password VARCHAR(255) NOT NULL,
        email_smtp_username VARCHAR(255) NOT NULL,
        email_subject VARCHAR(255) NOT NULL,
        url_envio VARCHAR(255) NOT NULL,
        PRIMARY KEY (parametro_email_id),
        CONSTRAINT UKigm5hgraw9ukfx9sptxuc8cw4 UNIQUE (email_from)
    )
    ENGINE=MyISAM DEFAULT CHARSET=latin1 DEFAULT COLLATE=latin1_swedish_ci;
    
INSERT IGNORE INTO parametros_email(parametro_email_id,config_set,email_from,email_from_name,email_port,email_smtp_host,email_smtp_password,email_smtp_username,email_subject,url_envio) VALUES
(default,'ConfigSet','tattoolsadm@gmail.com','Tattools',587,'smtp.gmail.com','t@20admSys','tattoolsadm@gmail.com','Tattools - E-mail Automatizado','http://localhost:8980/tattools/');
