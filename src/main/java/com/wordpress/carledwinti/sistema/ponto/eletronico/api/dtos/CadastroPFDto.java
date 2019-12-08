package com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Optional;

public class CadastroPFDto {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qtdHorasTrabalhadasDia = Optional.empty();
    private Optional<String> qtdHorasAlmocoDia = Optional.empty();
    private String cnpj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message="Nome nao pode ser vazio")
    @Length(message="Nome deve ter entre 4 e 100 caracteres.", min = 4, max = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message="Email nao pode ser vazio")
    @Length(message="Nome deve ter entre 10 e 80 caracteres.", min = 10, max = 80)
    @Email(message = "Email invalido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message="Senha nao pode ser vazio")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message="Cpf nao pode ser vazio")
    @CPF(message = "Cpf invalido")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getQtdHorasTrabalhadasDia() {
        return qtdHorasTrabalhadasDia;
    }

    public void setQtdHorasTrabalhadasDia(Optional<String> qtdHorasTrabalhadasDia) {
        this.qtdHorasTrabalhadasDia = qtdHorasTrabalhadasDia;
    }

    public Optional<String> getQtdHorasAlmocoDia() {
        return qtdHorasAlmocoDia;
    }

    public void setQtdHorasAlmocoDia(Optional<String> qtdHorasAlmocoDia) {
        this.qtdHorasAlmocoDia = qtdHorasAlmocoDia;
    }

    @NotEmpty(message="Cnpj nao pode ser vazio")
    @CNPJ(message = "Cnpj invalido")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "CadastroPFDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhadasDia=" + qtdHorasTrabalhadasDia +
                ", qtdHorasAlmocoDia=" + qtdHorasAlmocoDia +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
