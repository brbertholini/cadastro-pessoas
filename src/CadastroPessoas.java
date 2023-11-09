import java.io.*;
import java.util.Scanner;

public class CadastroPessoas {
    public static void main(String[] args) {

        int numeroAtual = 0;

        try (BufferedReader configReader = new BufferedReader(new FileReader("numeroCadastros.txt"))) {
            numeroAtual = Integer.parseInt(configReader.readLine());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de número de cadastros: " + e.getMessage());;
        }

        String arquivoPerguntas = "formulario.txt";

        boolean sair = false;
        Scanner scanner = new Scanner(System.in);

        while (!sair) {
            System.out.println("Menu:");
            System.out.println("1 - Cadastrar o usuário");
            System.out.println("2 - Listar todos usuários cadastrados");
            System.out.println("3 - Cadastrar nova pergunta no formulário");
            System.out.println("4 - Deletar pergunta do formulário");
            System.out.println("5 - Pesquisar usuário por nome ou idade ou email");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    try {
                        FileReader arquivoReader = new FileReader(arquivoPerguntas);
                        BufferedReader leitor = new BufferedReader(arquivoReader);

                        String linha;
                        int numeroPergunta = 1;

                        String nome = "";
                        String email = "";
                        int idade = 0;
                        double altura = 0.0;

                        while ((linha = leitor.readLine()) != null) {
                            System.out.println(linha);
                            System.out.print("Digite sua resposta: ");
                            String resposta = scanner.nextLine();
                            System.out.println("Resposta: " + resposta);
                            switch (numeroPergunta) {
                                case 1:
                                    nome = resposta;
                                    break;
                                case 2:
                                    email = resposta;
                                    break;
                                case 3:
                                    idade = Integer.parseInt(resposta);
                                    break;
                                case 4:
                                    altura = Double.parseDouble(resposta);
                                    break;
                            }
                            numeroPergunta++;
                        }

                        numeroAtual++;

                        try (BufferedWriter configWriter = new BufferedWriter(new FileWriter("numeroCadastros.txt"))) {
                            configWriter.write(String.valueOf(numeroAtual));
                        } catch (IOException e) {
                            System.err.println("Erro ao atualizar o arquivo de número de cadastros: " + e.getMessage());;
                        }

                        Usuario pessoa = new Usuario(nome, email, idade, altura);
                        System.out.println("Informações cadastradas:");
                        System.out.println("Nome: " + pessoa.getNome());
                        System.out.println("Email: " + pessoa.getEmail());
                        System.out.println("Idade: " + pessoa.getIdade());
                        System.out.println("Altura: " + pessoa.getAltura());

                        String nomeSemEspacos = nome.replace(" ", "");
                        String nomeFormatado = nomeSemEspacos.toUpperCase();

                        String cadastro = numeroAtual+ "-" + nomeFormatado + ".txt";

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cadastro))) {
                            writer.write("Nome: " + nome);
                            writer.newLine();
                            writer.write("Email: " + email);
                            writer.newLine();
                            writer.write("Idade: " + idade);
                            writer.newLine();
                            writer.write("Altura: " + altura);
                            writer.newLine();
                        } catch (IOException e) {
                            System.err.println("Erro ao criar o arquivo de dados do usuário: " + e.getMessage());
                        }
                        leitor.close();
                    } catch (IOException e) {
                        System.err.println("Erro ao cadastrar o usuário: " + e.getMessage());
                    }

                    break;
                case 2:
                    // Código para listar usuários cadastrados
                    break;
                case 3:
                    // Código para cadastrar nova pergunta no formulário
                    break;
                case 4:
                    // Código para deletar pergunta do formulário
                    break;
                case 5:
                    // Código para pesquisar usuário por nome, idade ou email
                    break;
                case 6:
                    sair = true; // Sai do loop e encerra o programa
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        }
    }
}

