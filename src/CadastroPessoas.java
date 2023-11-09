import java.io.*;
import java.util.Scanner;

public class CadastroPessoasApp {
    public static void main(String[] args) {

        int numeroAtual = 0;
        String nomeFormatado = "";
        String cadastro = "cadastros/" + numeroAtual + "-" + nomeFormatado + ".txt";

        try (BufferedReader configReader = new BufferedReader(new FileReader("numeroCadastros.txt"))) {
            numeroAtual = Integer.parseInt(configReader.readLine());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de número de cadastros: " + e.getMessage());
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
                            System.err.println("Erro ao atualizar o arquivo de número de cadastros: " + e.getMessage());
                        }

                        Usuario pessoa = new Usuario(nome, email, idade, altura);
                        System.out.println("Informações cadastradas:");
                        System.out.println("Nome: " + pessoa.getNome());
                        System.out.println("Email: " + pessoa.getEmail());
                        System.out.println("Idade: " + pessoa.getIdade());
                        System.out.println("Altura: " + pessoa.getAltura());


                        String nomeSemEspacos = nome.replace(" ", "");
                        nomeFormatado = nomeSemEspacos.toUpperCase();
                        cadastro = "cadastros/" + numeroAtual + "-" + nomeFormatado + ".txt";

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cadastro))) {
                            writer.write(nome);
                            writer.newLine();
                            writer.write(email);
                            writer.newLine();
                            writer.write("" + idade);
                            writer.newLine();
                            writer.write("" + altura);
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
                    File folder = new File("cadastros/");
                    File[] files = folder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".txt");
                        }
                    });

                    if (files != null && files.length > 0) {
                        System.out.println("Usuários cadastrados:");
                        for (int i = 0; i < files.length; i++) {
                            try (BufferedReader fileReader = new BufferedReader(new FileReader(files[i]))) {
                                String firstLine = fileReader.readLine();
                                System.out.println((i+1) + " - " + firstLine);
                            } catch (IOException e) {
                                System.err.println("Erro ao ler o arquivo: " + files[i].getName() + " - " + e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Nenhum usuário cadastrado.");
                    }
                    break;
                case 3:
                    try {
                        FileReader arquivoReader = new FileReader(arquivoPerguntas);
                        BufferedReader leitor = new BufferedReader(arquivoReader);

                        System.out.println("Perguntas atuais do formulário:");
                        String linha;
                        int numeroPergunta = 1;
                        while ((linha = leitor.readLine()) != null) {
                            System.out.println(linha);
                            numeroPergunta++;
                        }

                        System.out.print("Digite a nova pergunta: ");
                        String novaPergunta = scanner.nextLine();

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoPerguntas, true))) {
                            writer.newLine();
                            writer.write(numeroPergunta + " - " + novaPergunta);
                            System.out.println("Nova pergunta adicionada com sucesso!");
                        } catch (IOException e) {
                            System.err.println("Erro ao adicionar nova pergunta: " + e.getMessage());
                        }

                        leitor.close();

                    } catch (IOException e) {
                        System.err.println("Erro ao cadastrar nova pergunta: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        // Leitura do arquivo de perguntas
                        FileReader arquivoReader = new FileReader(arquivoPerguntas);
                        BufferedReader leitor = new BufferedReader(arquivoReader);

                        // Lista todas as perguntas atuais
                        System.out.println("Perguntas atuais no formulário:");
                        String linha;
                        int numeroPergunta = 1;
                        while ((linha = leitor.readLine()) != null) {
                            System.out.println(linha);
                            numeroPergunta++;
                        }

                        // Solicita ao usuário o número da pergunta a ser deletada
                        System.out.print("Digite o número da pergunta a ser deletada: ");
                        int numeroPerguntaDeletar = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha

                        // Verifica se a pergunta pode ser deletada
                        if (numeroPerguntaDeletar >= 5 && numeroPerguntaDeletar < numeroPergunta) {
                            // Lê todas as perguntas do arquivo, exceto a que será deletada
                            leitor.close(); // Fecha o leitor atual

                            arquivoReader = new FileReader(arquivoPerguntas);
                            leitor = new BufferedReader(arquivoReader);

                            StringBuilder novoConteudo = new StringBuilder();
                            numeroPergunta = 1;
                            while ((linha = leitor.readLine()) != null) {
                                if (numeroPergunta != numeroPerguntaDeletar) {
                                    novoConteudo.append(linha).append(System.lineSeparator());
                                }
                                numeroPergunta++;
                            }

                            // Sobrescreve o arquivo com as perguntas atualizadas
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoPerguntas))) {
                                writer.write(novoConteudo.toString().trim());
                                System.out.println("Pergunta deletada com sucesso!");
                            } catch (IOException e) {
                                System.err.println("Erro ao sobrescrever o arquivo de perguntas: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Pergunta padrão, não pode ser deletada. Só podem ser deletadas as perguntas da 5ª em diante.");
                        }

                        leitor.close();
                    } catch (IOException e) {
                        System.err.println("Erro ao deletar pergunta: " + e.getMessage());
                    }
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
