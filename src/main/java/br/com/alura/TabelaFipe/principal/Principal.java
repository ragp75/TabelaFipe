package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Anos;
import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.FipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private FipeService fipeService;

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    public void exibeMenu(){
        String opcoes = """
                Digite a opcao desejada:
                Motos
                Carros
                Caminhões                
                
                >""";
        System.out.print(opcoes);
        String opcao = leitura.nextLine();
        String endereco;
        List<Dados> listaDeMarcas;
        String veiculo="";
        if (opcao.toLowerCase().contains("carr")){
            veiculo = "carros";
            listaDeMarcas = fipeService.getMarcaCarros();
        } else if (opcao.toLowerCase().contains("mot")) {
            veiculo = "motos";
            listaDeMarcas = fipeService.getMarcaMotos();
        } else {
            veiculo = "caminhoes";
            listaDeMarcas = fipeService.getMarcasCaminhoes();
        }

        System.out.println("Marcas:");
        listaDeMarcas.forEach(System.out::println);
        System.out.print("\nInforme o código da marca " +
                "de " +veiculo+
                " que você deseja:\n>");
        String marca = leitura.nextLine();

        Modelos modelos = fipeService.getModelos(veiculo, marca);

        System.out.println("Modelos:"+modelos);

        modelos.modelos().stream().sorted(Comparator.comparing(Dados::nome)).forEach(System.out::println);

        System.out.println("\nDigite um trecho do modelo que você pocura:\n>");

        String  trechoModelo = leitura.nextLine();

        modelos.modelos().stream().filter(modelo -> modelo.nome().toLowerCase().contains(trechoModelo.toLowerCase())).forEach(System.out::println);

        System.out.println("\nDigite o código do modelo desejado:\n>");

        String  codigoModelo = leitura.nextLine();

        List<Dados> anos = fipeService.getAnosDoModelo(veiculo, marca, codigoModelo);
        String finalVeiculo = veiculo;
        anos.stream().sorted(Comparator.comparing(Dados::codigo)).forEach(c->{
            Veiculo avaliacaoVeiculo = fipeService.getVeiculo(finalVeiculo, marca, codigoModelo, c.codigo());
            System.out.println("\nVeiculo:\n"+avaliacaoVeiculo);
        });
//
//        System.out.println("\nAnos do modelo escolhido:\n");
//
//        anos.forEach(System.out::println);
//
//
//
//        System.out.println("\nDigite o ano do veiculo:\n>");
//        String ano = leitura.nextLine();
//        String finalAno = ano;
//        var anosRetornados = anos.stream().filter(c->c.nome().toLowerCase().contains(finalAno.toLowerCase())).collect(Collectors.toList());
//
//        System.out.println("Avaliações do veiculo escolhido:\n");
//
//        anosRetornados.forEach(System.out::println);
//
//
//        anosRetornados.stream().forEach(c->{
//            Veiculo avaliacaoVeiculo = fipeService.getVeiculo(finalVeiculo, marca, codigoModelo, c.codigo());
//            System.out.println("Veiculo:\n"+avaliacaoVeiculo);
//        });

//        if(anoRetornado.isPresent()){
//            ano = anoRetornado.get().codigo();
//        }
//        System.out.println("Ano do veiculo escolhido, codigo:"+ano);
//        Veiculo avaliacaoVeiculo = fipeService.getVeiculo(veiculo, marca, codigoModelo, ano);
//
//        System.out.println("\nAvaliação do veículo:\n"+avaliacaoVeiculo);
    }
}
