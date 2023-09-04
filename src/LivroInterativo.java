import java.io.*;
import java.util.*;

public class LivroInterativo {
    private Map<String, Personagem> personagens;
    private Map<String, Capitulo> capitulos;
    private String progressoAtual;
    private String arquivoProgresso = "progresso.txt"; // Nome do arquivo de progresso

    public LivroInterativo() {
        personagens = new HashMap<>();
        capitulos = new HashMap<>();
        progressoAtual = "Capitulo1"; // Define o capítulo inicial
    }

    public void carregarPersonagens(String arquivoPersonagens) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivoPersonagens));
        String linha;
        
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(",");
            if (partes.length == 2) {
                String nome = partes[0];
                int energia = Integer.parseInt(partes[1]);
                Personagem personagem = new Personagem(nome, energia);
                personagens.put(nome, personagem);
            }
        }

        reader.close();
    }

    public void carregarCapitulos(String arquivoCapitulos) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivoCapitulos));
        String linha;
        
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(",");
            if (partes.length == 3) {
                String nome = partes[0];
                String texto = partes[1];
                String[] escolhas = partes[2].split("\\|");
                Capitulo capitulo = new Capitulo(nome, texto, Arrays.asList(escolhas));
                capitulos.put(nome, capitulo);
            }
        }

        reader.close();
    }

    public void mostrarCapitulo(String nomeCapitulo) {
        Capitulo capitulo = capitulos.get(nomeCapitulo);
        if (capitulo != null) {
            System.out.println(capitulo.getTexto());
            List<String> escolhas = capitulo.getEscolhas();
            for (int i = 0; i < escolhas.size(); i++) {
                System.out.println((i + 1) + ". " + escolhas.get(i));
            }

            
            int escolhaJogador = -1; 

            while (escolhaJogador < 1 || escolhaJogador > escolhas.size()) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Escolha uma opção: ");
                    escolhaJogador = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Tente novamente.");
                }
            }

            String proximoCapitulo = escolhas.get(escolhaJogador - 1);
            progressoAtual = proximoCapitulo;
            System.out.println("\nVocê escolheu: " + proximoCapitulo + "\n");
        } else {
            System.out.println("Capítulo não encontrado.");
        }
    }

    public void salvarProgresso() {
        try {
            FileWriter fileWriter = new FileWriter(arquivoProgresso);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Escreva o nome do capítulo atual no arquivo de progresso
            bufferedWriter.write(progressoAtual);

            bufferedWriter.close();
            System.out.println("Progresso salvo.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o progresso: " + e.getMessage());
        }
    }

    public void carregarProgresso() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivoProgresso));
            String linha = reader.readLine();
            if (linha != null) {
                progressoAtual = linha;
                System.out.println("Progresso carregado: " + progressoAtual);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Nenhum progresso anterior encontrado.");
        }
    }

    public void iniciarJogo() {
        while (true) {
            Capitulo capituloAtual = capitulos.get(progressoAtual);
            if (capituloAtual == null) {
                System.out.println("Fim da história.");
                break;
            }

            mostrarCapitulo(progressoAtual);

            // Implemente aqui a lógica para salvar o progresso atual
            salvarProgresso();
        }
    }

    public static void main(String[] args) {
        LivroInterativo livro = new LivroInterativo();

        try {
            livro.carregarPersonagens("personagens.txt");
            livro.carregarCapitulos("capitulos.txt");
            livro.carregarProgresso(); // Carrega o progresso salvo, se houver

            livro.iniciarJogo();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
