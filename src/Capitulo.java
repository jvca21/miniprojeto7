import java.util.List;

public class Capitulo {
    private String nome;
    private String texto;
    private List<String> escolhas;

    public Capitulo(String nome, String texto, List<String> escolhas) {
        this.nome = nome;
        this.texto = texto;
        this.escolhas = escolhas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<String> getEscolhas() {
        return escolhas;
    }

    public void setEscolhas(List<String> escolhas) {
        this.escolhas = escolhas;
    }

    // Implemente outros métodos ou lógica específica do capítulo, se necessário
}
