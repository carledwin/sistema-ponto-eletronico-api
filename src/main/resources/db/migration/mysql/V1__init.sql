    drop table if exists empresa;
    drop table if exists funcionario;
    drop table if exists lancamento;
    
    create table empresa (
        id bigint not null auto_increment,
        cnpj varchar(255),
        data_atualizacao datetime,
        data_criacao datetime,
        razao_social varchar(255),
        primary key (id)
    );
    
    create table funcionario (
        id bigint not null auto_increment,
        cpf varchar(255),
        data_atualizacao datetime,
        data_criacao datetime,
        email varchar(255),
        nome varchar(255),
        perfil_enum varchar(255),
        qtd_horas_almoco float,
        qtd_horas_trabalho_dia float,
        senha varchar(255),
        valor_hora decimal(19,2),
        empresa_id bigint,
        primary key (id)
    );
    
    create table lancamento (
        id bigint not null auto_increment,
        data datetime,
        data_atualizacao datetime,
        data_criacao datetime,
        descricao varchar(255),
        lancamento_enum varchar(255),
        localizacao varchar(255),
        funcionario_id bigint,
        primary key (id)
    );
    
    
    alter table funcionario 
        add constraint FK4cm1kg523jlopyexjbmi6y54j 
        foreign key (empresa_id) 
        references empresa (id);
        
    alter table lancamento 
        add constraint FK46i4k5vl8wah7feutye9kbpi4 
        foreign key (funcionario_id) 
        references funcionario (id);