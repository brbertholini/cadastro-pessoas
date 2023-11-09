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

        numeroAtual++;

        try (BufferedWriter configWriter = new BufferedWriter(new FileWriter("numeroCadastros.txt"))) {
            configWriter.write(String.valueOf(numeroAtual));
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo de número de cadastros: " + e.getMessage());;
        }

        String arquivoPerguntas = "formulario.txt";

        try {
            FileReader arquivoReader = new FileReader(arquivoPerguntas);
            BufferedReader leitor = new BufferedReader(arquivoReader);
            Scanner scanner = new Scanner(System.in);

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
            scanner.close();
        } catch (IOException e) {

        }
    }
}