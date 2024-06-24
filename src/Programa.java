import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Endereco {
    private String rua;
    private String cidade;
    private String codigoDono;

    public Endereco(String rua, String cidade, String codigoDono) {
        this.rua = rua;
        this.cidade = cidade;
        this.codigoDono = codigoDono;
    }

    public String getRua() {
        return rua;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCodigoDono() {
        return codigoDono;
    }

    public void setCodigoDono(String codigoDono) {
        this.codigoDono = codigoDono;
    }
}

class Pessoa {
    private String codigo;
    private String nome;

    public Pessoa(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
}

public class Programa {
    public static void main(String[] args) throws IOException {
        List<Pessoa> pessoas = lerPessoas("Pessoas CSV.txt");
        List<Endereco> enderecos = lerEnderecos("Endereços CSV.txt");

        // Associar pessoas aos endereços
        List<String> linhas = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            for (Pessoa pessoa : pessoas) {
                if (endereco.getCodigoDono().equals(pessoa.getCodigo())) {
                    String linha = pessoa.getCodigo() + ", " + pessoa.getNome() + ", " + endereco.getRua() + ", " + endereco.getCidade();
                    linhas.add(linha);
                    break;
                }
            }
        }

        // Gerar arquivo C.txt
        gerarArquivoC(linhas, "C.txt");
    }

    private static List<Pessoa> lerPessoas(String arquivo) throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine(); // Pular a linha do cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.trim().split("\\s+");
                if (campos.length >= 2) {
                    Pessoa pessoa = new Pessoa(campos[0], campos[1]);
                    pessoas.add(pessoa);
                } else {
                    System.out.println("Linha inválida no arquivo de pessoas: " + linha);
                }
            }
        }
        return pessoas;
    }

    private static List<Endereco> lerEnderecos(String arquivo) throws IOException {
        List<Endereco> enderecos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            br.readLine(); // Pular a linha do cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.trim().split("\\s+");
                if (campos.length >= 3) {
                    Endereco endereco = new Endereco(campos[0], campos[1], campos[2]);
                    enderecos.add(endereco);
                } else {
                    System.out.println("Linha inválida no arquivo de endereços: " + linha);
                }
            }
        }
        return enderecos;
    }

    private static void gerarArquivoC(List<String> linhas, String arquivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            bw.write("Cod, Nome, Rua, Cidade");
            bw.newLine();
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        }
    }
}
