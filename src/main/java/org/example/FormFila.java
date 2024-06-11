package org.example;

import java.io.*;
import java.util.*;

public class FormFila {
    Queue<Pessoa> filaUrgente = new ArrayDeque<>();
    Queue<Pessoa> filaPoucoUrgente = new ArrayDeque<>();
    List<String> historicoChamadas = new ArrayList<>();
    private static final String CSV_FILE = "dados.csv";

    public FormFila() {
        carregaArquivo();
        menu();
    }

    private void carregaArquivo() {
        String line;
        String[] leitura;
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                Pessoa p = new Pessoa();
                leitura = line.split(",");
                p.setNome(leitura[0]);
                p.setRg(leitura[1]);
                p.setIdade(Integer.parseInt(leitura[2]));
                p.setGravidade(leitura[3]);
                addFila(p);
            }
            mostraFilas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostraFilas() {
        System.out.println("Fila Urgente:");
        for (Pessoa p : filaUrgente) {
            System.out.println(p);
        }
        System.out.println("Fila Pouco Urgente:");
        for (Pessoa p : filaPoucoUrgente) {
            System.out.println(p);
        }
    }

    private void addFila(Pessoa p) {
        if ("Urgente".equalsIgnoreCase(p.getGravidade())) {
            filaUrgente.add(p);
        } else {
            filaPoucoUrgente.add(p);
        }
    }

    private void salvarArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Pessoa p : filaUrgente) {
                bw.write(p.toString().replace(":", ","));
                bw.newLine();
            }
            for (Pessoa p : filaPoucoUrgente) {
                bw.write(p.toString().replace(":", ","));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atender() {
        Pessoa atendido = null;
        if (!filaUrgente.isEmpty()) {
            atendido = filaUrgente.poll();
        } else if (!filaPoucoUrgente.isEmpty()) {
            atendido = filaPoucoUrgente.poll();
        }

        if (atendido != null) {
            System.out.println("Atendendo: " + atendido.getNome());
            historicoChamadas.add(atendido.toString());
            salvarArquivo();
        } else {
            System.out.println("Nenhum paciente na fila.");
        }
    }

    private void atualizarPrioridade(String rg, String novaGravidade) {
        for (Pessoa p : filaUrgente) {
            if (p.getRg().equals(rg)) {
                p.setGravidade(novaGravidade);
                filaUrgente.remove(p);
                addFila(p);
                salvarArquivo();
                return;
            }
        }

        for (Pessoa p : filaPoucoUrgente) {
            if (p.getRg().equals(rg)) {
                p.setGravidade(novaGravidade);
                filaPoucoUrgente.remove(p);
                addFila(p);
                salvarArquivo();
                return;
            }
        }

        System.out.println("Paciente não encontrado ou inexistente!");
    }

    private void verHistorico() {
        System.out.println("Histórico de Chamadas:");
        for (String registro : historicoChamadas) {
            System.out.println(registro);
        }
    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Paciente");
            System.out.println("2. Atender Paciente");
            System.out.println("3. Atualizar Prioridade");
            System.out.println("4. Ver Filas");
            System.out.println("5. Ver Histórico de Chamadas");
            System.out.println("0. Sair");
            System.out.print("Escolha algumas dessas opções: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarPaciente(scanner);
                    break;
                case 2:
                    atender();
                    break;
                case 3:
                    System.out.print("RG do paciente: ");
                    String rg = scanner.nextLine();
                    System.out.print("Nova gravidade | (Urgente/Pouco Urgente): ");
                    String novaGravidade = scanner.nextLine();
                    atualizarPrioridade(rg, novaGravidade);
                    break;
                case 4:
                    mostraFilas();
                    break;
                case 5:
                    verHistorico();
                    break;
                case 0:
                    System.out.println("saindo");
                    break;
                default:
                    System.out.println("inválida");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private void adicionarPaciente(Scanner scanner) {
        Pessoa p = new Pessoa();
        System.out.print("Nome: ");
        p.setNome(scanner.nextLine());
        System.out.print("RG: ");
        p.setRg(scanner.nextLine());
        System.out.print("Idade: ");
        p.setIdade(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Gravidade | Urgente/Pouco Urgente: ");
        p.setGravidade(scanner.nextLine());
        addFila(p);
        salvarArquivo();
        mostraFilas();
    }

    public static void main(String[] args) {
        new FormFila();
    }
}
