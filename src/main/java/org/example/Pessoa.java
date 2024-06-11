package org.example;

public class Pessoa {
    private String nome;
    private String rg;
    private int idade;
    private String gravidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    @Override
    public String toString() {
        return getNome() + ":" + getRg() + ":" + getIdade() + ":" + getGravidade();
    }
}
