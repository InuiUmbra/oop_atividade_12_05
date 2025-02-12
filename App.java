package atividade_oop_12_05;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;

public class App {
    private Set<Funcionario> listaFuncionarios = new HashSet<Funcionario>();

    public static void main(String[] args) {
        App app = new App();    //Instanciação da classe para ter acesso aos métodos sem ter que colocá-los como static.
        int menu = 0;

        do{
            menu = Integer.parseInt(JOptionPane.showInputDialog("Escolha a opção desejada:\n" +
                "1 - Cadastrar novo funcionário\n" +
                "2 - Adicionar novo dependente a usuário já existente\n" +
                "3 - Exibir todos os funcionários cadastrados\n" +
                "4 - Exibir todos os dependentes de um funcionário\n" +
                "5 - Remover o dependente de um funcionário\n" +
                "0 - Sair"));

            switch (menu){
                case 1:
                    app.cadastrarFuncionario();
                break;

                case 2:
                    String adicionarDependentes = app.adicionarDependente();
                    JOptionPane.showMessageDialog(null,adicionarDependentes);
                break;

                case 3:
                    app.exibirFuncionarios();
                break;

                case 4:
                    String exibirDependentes = app.exibirDependentes();
                    JOptionPane.showMessageDialog(null, exibirDependentes);
                break;

                case 5:
                String removerDependentes = app.removerDependentes();
                JOptionPane.showMessageDialog(null, removerDependentes);    
                break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Fechando programa");
                break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida, saindo do programa");
                    menu = 0;
                break;
            }
        }while(menu > 0);
    }
    
    private void cadastrarFuncionario(){
        String nome = JOptionPane.showInputDialog("Insira o nome do funcionário");
        int CPF = Integer.parseInt(JOptionPane.showInputDialog("Insira o CPF do funcionário"));
        boolean duplicata = checaDuplicataFuncionario(CPF);
        if (duplicata == true){
            JOptionPane.showMessageDialog(null, "Erro: CPF já cadastrado");
        }else{
            String estadoCivil = JOptionPane.showInputDialog("Insira o estado civil do funcionário");
            String perguntaDependentes = JOptionPane.showInputDialog("Possui dependentes? (S/N)");
            if(perguntaDependentes.equalsIgnoreCase("s")){
                String nomeDependente = JOptionPane.showInputDialog("Informe o nome do dependente");
                boolean duplicataDependente = checaDuplicataDependente(CPF, nomeDependente);
                if(duplicataDependente == true){
                    JOptionPane.showMessageDialog(null, "Erro: Dependente já cadastrado");
                }else{
                    String parentesco = JOptionPane.showInputDialog("Informe o parentesco");
                    String nascimento = JOptionPane.showInputDialog("Insira a data de nascimento do dependente");
                    Dependentes dependentes = new Dependentes(nomeDependente, parentesco, nascimento);
                    listaFuncionarios.add(new Funcionario(nome, CPF, estadoCivil, dependentes));
                    JOptionPane.showMessageDialog(null, "Funcionário " + nome + " com CPF: " + CPF + " registrado com sucesso!");
                }   
            }else if(perguntaDependentes.equalsIgnoreCase("n")){
                listaFuncionarios.add(new Funcionario(nome, CPF, estadoCivil));
                JOptionPane.showMessageDialog(null, "Funcionário " + nome + " com CPF: " + CPF + " registrado com sucesso!");
            }
        }
    }

    private String adicionarDependente(){
        int cpfDesejado = Integer.parseInt(JOptionPane.showInputDialog("Informe o CPF do funcionário desejado"));
        for (Funcionario cadastrado : listaFuncionarios){
            if(cadastrado.getCPF() == cpfDesejado){
                String nomeDependente = JOptionPane.showInputDialog("Informe o nome do dependente");
                boolean duplicata = checaDuplicataDependente(cadastrado.getCPF(), nomeDependente);
                if (duplicata == false){
                    String parentesco = JOptionPane.showInputDialog("Informe o parentesco");
                    String nascimento = JOptionPane.showInputDialog("Informe a data de nascimento");
                    Dependentes dependentes = new Dependentes(nomeDependente, parentesco, nascimento);
                    cadastrado.listaDependentes.add(dependentes);
                    return "Dependente registrado com sucesso!";
                }else{
                    return "Erro: Dependente já cadastrado";
                }
            }
        }
        return "ERRO: CPF não cadastrado ou inválido";
    }

    private void exibirFuncionarios(){
        String todosFuncionarios = "Todos os funcionários atualmente cadastrados são: ";
        for(Funcionario cadastrado : listaFuncionarios){
            todosFuncionarios += "\n"  + cadastrado.getNome() + " com CPF: " + cadastrado.getCPF() + ". Com matrícula número: " + cadastrado.getMatricula();
        }
        JOptionPane.showMessageDialog(null, todosFuncionarios);
    }

    private String exibirDependentes(){
        int cpfAConsultar = Integer.parseInt(JOptionPane.showInputDialog("Informe o CPF do funcionário a ser consultado"));
        String listarDependentesNulo = "Este funcionário não possui dependentes cadastrados";  //Declarada em escopo do método inteiro já que será imutável.
        for(Funcionario cadastrado : listaFuncionarios){
            if (cpfAConsultar == cadastrado.getCPF()){
                int existeDependente = 0;  //Flag somente declarada caso o CPF seja validado.
                String listarDependentes = "Todos os dependentes atualmente cadastrados para este funcionário são:";
                for(Dependentes todosDependentes : cadastrado.listaDependentes){
                    if(todosDependentes != null){
                            listarDependentes += "\n" + todosDependentes.getNomeDependente() + ". Com parentesco: " + todosDependentes.getParentesco();
                            existeDependente = 1;
                    }
                }
                if(existeDependente == 0){  //Uso de uma flag para determinar se a lista estava vazia
                    return listarDependentesNulo;
                }
                return listarDependentes;
            }
        }
        return "CPF inválido";
    }


    private String removerDependentes(){
        int cpfConsultado = Integer.parseInt(JOptionPane.showInputDialog("Informe o CPF do funcionário que deseja remover o dependente"));
        String dependenteARemover = JOptionPane.showInputDialog("Informe o nome do dependente a ser removido");
        for(Funcionario cadastrado : listaFuncionarios){
            Iterator<Dependentes> iterador = cadastrado.listaDependentes.iterator();
            if(cpfConsultado == cadastrado.getCPF()){
                while(iterador.hasNext()){
                    Dependentes dependente = iterador.next();
                    if (dependente.getNomeDependente().equals(dependenteARemover)){
                        iterador.remove();
                        return "Dependente de nome " + dependenteARemover + " foi removido(a) com sucesso";
                    }
                }
            }
        }
        return "ERRO: CPF inválido ou funcionário não possui dependentes registrados";
    }

    private boolean checaDuplicataFuncionario(int cpf){
        for(Funcionario cadastrado : listaFuncionarios){
            if(cpf == cadastrado.getCPF()){
                return true;
            }
        }
        return false;
    }

    private boolean checaDuplicataDependente(int cpf, String nomeDependente){
        for(Funcionario cadastrado : listaFuncionarios){
            if (cpf == cadastrado.getCPF()){
                for(Dependentes dependenteCadastrado : cadastrado.listaDependentes){
                    if(dependenteCadastrado.getNomeDependente().equals(nomeDependente)){
                        return true;
                    }
                }
            }
        }
        return false;        
    }
}
