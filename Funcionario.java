package atividade_oop_12_05;

import java.util.HashSet;
import java.util.Set;

public class Funcionario {
    private String nome;
    private int CPF;
    private String estadoCivil;
    private int matricula = 0;
    private static int contador = 0;
    Set<Dependentes> listaDependentes = new HashSet<Dependentes>(); //Nível de acesso package-protected porque não consegui resolver alguns problemas de acesso.

    public Funcionario(String nome, int CPF, String estadoCivil, Dependentes dependentes){
        this.nome = nome;
        this.CPF = CPF;
        this.estadoCivil = estadoCivil;
        this.matricula = ++contador;
        listaDependentes.add(dependentes);

    }

    public Funcionario(String nome, int CPF, String estadoCivil){
        this.nome = nome;
        this.CPF = CPF;
        this.estadoCivil = estadoCivil;
        this.matricula = ++contador;
    }

    public String getNome(){
        return nome;
    }

    public int getCPF(){
        return CPF;
    }

    public int getMatricula(){
        return matricula;
    }
}