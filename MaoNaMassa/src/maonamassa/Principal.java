/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package maonamassa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

class Funcionario extends Pessoa {
    BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }
}

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 - Inserir todos os funcionários
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 10, 12), new BigDecimal("2000.00"), "Desenvolvedor"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 5, 20), new BigDecimal("3000.00"), "Gerente"));
        funcionarios.add(new Funcionario("José", LocalDate.of(1995, 3, 15), new BigDecimal("1800.00"), "Analista"));
        funcionarios.add(new Funcionario("Ana", LocalDate.of(1992, 11, 30), new BigDecimal("2500.00"), "Desenvolvedor"));

        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        System.out.println("Funcionários:");
        for (Funcionario f : funcionarios) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.printf("Nome: %s, Data de Nascimento: %s, Salário: R$ %,.2f, Função: %s%n",
                    f.getNome(), f.getDataNascimento().format(formatter), f.getSalario(), f.getFuncao());
        }

        // 3.4 - Atualizar os salários com 10% de aumento
        for (Funcionario f : funcionarios) {
            f.salario = f.getSalario().multiply(new BigDecimal("1.1"));
        }

        // 3.5 - Agrupar os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                System.out.printf("- Nome: %s, Salário: R$ %,.2f%n", f.getNome(), f.getSalario());
            }
            System.out.println();
        }

        // 3.8 - Imprimir os funcionários que fazem aniversário em outubro e dezembro
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        for (Funcionario f : funcionarios) {
            if (f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12) {
                System.out.printf("Nome: %s, Data de Nascimento: %s%n", f.getNome(), f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

        // 3.9 - Imprimir o funcionário com a maior idade
        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .max(Comparator.comparing(f -> f.getDataNascimento().until(LocalDate.now()).getYears()))
                .orElse(null);
        if (funcionarioMaisVelho != null) {
            System.out.printf("\nFuncionário mais velho: Nome: %s, Idade: %d anos%n",
                    funcionarioMaisVelho.getNome(), funcionarioMaisVelho.getDataNascimento().until(LocalDate.now()).getYears());
        }

        // 3.10 - Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Pessoa::getNome))
                .forEach(f -> System.out.printf("Nome: %s, Salário: R$ %,.2f, Função: %s%n",
                        f.getNome(), f.getSalario(), f.getFuncao()));

        // 3.11 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal de salários: R$ %,.2f%n", totalSalarios);

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários mínimos por funcionário:");
        for (Funcionario f : funcionarios) {
            BigDecimal salariosMinimosFuncionario = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.printf("Nome: %s, Salários mínimos: %.2f%n", f.getNome(), salariosMinimosFuncionario);
        }
    }
}

