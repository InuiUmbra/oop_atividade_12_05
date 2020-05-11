package atividade_oop_12_05;

public class Dependentes{
    private String parentesco;
    private String nomeDependente;
    private String nascimento;

    public Dependentes(String nomeDependente, String parentesco, String nascimento){
        this.nomeDependente = nomeDependente;
        this.parentesco = parentesco;
        this.nascimento = nascimento;
    }

    public String getNomeDependente(){
        return nomeDependente;
    }

    public String getParentesco(){
        return parentesco;
    }

    public String getNascimento(){
        return nascimento;
    }
}