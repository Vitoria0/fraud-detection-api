package com.vitoria.ai.frauddetection.weka;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import java.util.ArrayList;

import weka.classifiers.Classifier;

public class DeteccaoDeFraudeBancaria {
    private Classifier classificador;
    private Instances dadosTreinamento;

    private Attribute atributoValor;
    private Attribute atributoOrigem;
    private Attribute atributoFraude;

    public void definirAtributos(){
        atributoValor = new Attribute("valor");

        ArrayList<String> valoresOrigem = new ArrayList<>();
        valoresOrigem.add("internacional");
        valoresOrigem.add("nacional");
        atributoOrigem = new Attribute("origem", valoresOrigem);

        ArrayList<String> valoresFraude = new ArrayList<>();
        valoresFraude.add("nao");
        valoresFraude.add("sim");
        atributoFraude = new Attribute("fraude", valoresFraude);

        ArrayList<Attribute> atributos = new ArrayList<>();
        atributos.add(atributoValor);
        atributos.add(atributoOrigem);
        atributos.add(atributoFraude);

        dadosTreinamento = new Instances("transacoes", atributos, 0);

        dadosTreinamento.setClassIndex(dadosTreinamento.numAttributes() - 1);
    }

    private void adicionarTransacao(double valor, String origem, String fraude){
        Instance instancia = new DenseInstance(dadosTreinamento.numAttributes());
        instancia.setDataset(dadosTreinamento);
        instancia.setValue(atributoValor, valor);
        instancia.setValue(atributoOrigem, origem);
        instancia.setValue(atributoFraude, fraude);
        dadosTreinamento.add(instancia);
    }

    public void adicionarExemplos() {
        adicionarTransacao(5000, "internacional", "sim");
        adicionarTransacao(6500, "internacional", "sim");
        adicionarTransacao(7000, "internacional", "sim");
        adicionarTransacao(7500, "internacional", "sim");
        adicionarTransacao(9000, "internacional", "sim");
        adicionarTransacao(10000, "internacional", "sim");
        adicionarTransacao(12000, "internacional", "sim");
        adicionarTransacao(15000, "internacional", "sim");
        adicionarTransacao(20000, "internacional", "sim");
        adicionarTransacao(8500, "internacional", "sim");

        adicionarTransacao(50, "nacional", "nao");
        adicionarTransacao(100, "nacional", "nao");
        adicionarTransacao(150, "nacional", "nao");
        adicionarTransacao(200, "nacional", "nao");
        adicionarTransacao(300, "nacional", "nao");
        adicionarTransacao(500, "nacional", "nao");
        adicionarTransacao(800, "nacional", "nao");
        adicionarTransacao(1200, "nacional", "nao");
        adicionarTransacao(2500, "nacional", "nao");
        adicionarTransacao(3000, "nacional", "nao");
    }

    public void treinamentoModelo() throws Exception{
        classificador = new J48();
        classificador.buildClassifier(dadosTreinamento);
    }

    public String classificarTransacao(double valor, String origem) throws Exception {
        Instance novaInstancia = new DenseInstance(dadosTreinamento.numAttributes());
        novaInstancia.setDataset(dadosTreinamento);
        novaInstancia.setValue(atributoValor, valor);
        novaInstancia.setValue(atributoOrigem, origem);

        double previsao = classificador.classifyInstance(novaInstancia);
        String resultado = dadosTreinamento.classAttribute().value((int) previsao);

        return """
                -----------------------------------
                Valor: R$ %.2f
                Origem: %s
                Resultado: %s
                -----------------------------------
               """.formatted(valor,origem,resultado.equals("sim")
                ? "FRAUDE DETECTADA" : "TRANSAÇÃO SEGURA");
    }

}
