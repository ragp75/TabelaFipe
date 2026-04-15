package br.com.alura.TabelaFipe.model;
/*
{
	"TipoVeiculo": 1,
	"Valor": "R$ 46.895,00",
	"Marca": "Fiat",
	"Modelo": "ARGO 1.0 6V Flex",
	"AnoModelo": 2018,
	"Combustivel": "Flex",
	"CodigoFipe": "001509-1",
	"MesReferencia": "abril de 2026",
	"SiglaCombustivel": "F"
}
 */
public record Veiculo(
        String TipoVeiculo,
        String Valor,
                String Marca,
                String Modelo,
                String AnoModelo,
                String Combustivel,
                String CodigoFipe,
                String MesReferencia,
                String SiglaCombustivel) {
}
