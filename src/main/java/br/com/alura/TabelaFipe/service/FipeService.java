package br.com.alura.TabelaFipe.service;

import br.com.alura.TabelaFipe.model.Anos;
import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FipeService {

    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConverteDados converteDados;

    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private static final String QUERY_CARROS_MARCAS= "carros/marcas";
    private static final String QUERY_CAMINHOES_MARCAS= "caminhoes/marcas";
    private static final String QUERY_MOTOS_MARCAS= "motos/marcas";

    // get data from fipe url
    // convert json response to a list of Dados
    // return list sorted by name
    public List<Dados> getDados(String endereco) {
        var result = consumoAPI.consumoAPI(endereco);
        var dados = converteDados.obterLista(result, Dados.class);
        return dados.stream().sorted(Comparator.comparing(Dados::nome)).collect(Collectors.toList());
    }

    public @Nullable List<Dados> getMarcaMotos() {
        return getDados(URL_BASE + QUERY_MOTOS_MARCAS);
    }

    public @Nullable List<Dados> getMarcasCaminhoes() {
        return getDados(URL_BASE + QUERY_CAMINHOES_MARCAS);
    }

    public @Nullable List<Dados> getMarcaCarros() {
        return getDados(URL_BASE + QUERY_CARROS_MARCAS);
    }

    //https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos
    public Modelos getModelos(String veiculo, String marca) {
        return getModelosData(URL_BASE + veiculo + "/marcas/" + marca +"/modelos");
    }

    private Modelos getModelosData(String endereco) {
        var result = consumoAPI.consumoAPI(endereco);
        System.out.println(result);
        var modelos = converteDados.obterDados(result, Modelos.class);
        return modelos;
        //TODO do I need to sort?
        //.modelos() stream().sorted(Comparator.comparing(Dados::nome)).collect(Collectors.toList());
    }

    private List<Dados> getAnosModelos(String endereco) {
        var result = consumoAPI.consumoAPI(endereco);
        System.out.println(result);
        var anos = converteDados.obterLista(result, Dados.class);
        return anos;
    }

    public List<Dados> getAnosDoModelo(String veiculo, String marca, String codigoModelo) {
        return getAnosModelos(URL_BASE + veiculo + "/marcas/" + marca +"/modelos/"+ codigoModelo+"/anos");
    }

    public Veiculo getVeiculo(String veiculo, String marca, String codigoModelo, String ano) {
        return getVeiculo(URL_BASE + veiculo + "/marcas/" + marca +"/modelos/"+ codigoModelo+"/anos/" + ano);
    }

    private Veiculo getVeiculo(String endereco) {
        var result = consumoAPI.consumoAPI(endereco);
        System.out.println(result);
        var veiculo = converteDados.obterDados(result, Veiculo.class);
        return veiculo;
    }
}
