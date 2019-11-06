package zuul;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo
 */
public class ManipulaArquivoTexto {
    private File arquivo;

    public ManipulaArquivoTexto(String nome) {
        this.arquivo = new File(nome);
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }
    
    //FunÃ§Ã£o para ler arquivo completo e retorna uma String contendo todo o arquivo,caso exista.Quando o arquivo nÃ£o existe retorna erro.
    public String lerArquivoCompleto(){
        String texto = "";
        String linha;
        try {
            Scanner input = new Scanner(arquivo);
            while(input.hasNext()){
                linha = input.nextLine();
                texto += linha + "\n";
            }
            return texto;
        } catch (FileNotFoundException ex) {
            return ex.getMessage();
        }
    }
    
    //FunÃ§Ã£o para ler um arquivo por linhas.Essa funÃ§Ã£o usa um ArrayList para receber e retornar cada linha lida.
    public ArrayList<String> lerArquivoLinhas(){
        ArrayList<String> linhas = new ArrayList<>();
        
        try {
            Scanner input = new Scanner(arquivo);
            
            while(input.hasNext()){
                linhas.add(input.nextLine());
            }
            
            return linhas;
            
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ManipulaArquivoTexto.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    //FunÃ§Ã£o para salvar os dados do arquivo apartir de uma string recebida como parametro, com retorno para o usuario se foi bem sucedida a operaÃ§Ã£o.
    public String salvarDados(String dados){
        try {
            Formatter gravar = new Formatter(arquivo);
            gravar.format("%s",dados);
            gravar.flush();
            gravar.close();
            return "Salvo com sucesso.";
            
        } catch (FileNotFoundException ex) {
            return ex.getMessage();
        }
    }
    
    //FunÃ§Ã£o para salvar os dados do arquivo apartir de um ArrayList que contÃ©m oque serÃ¡ escrito em cada linha.
    public String salvarDados(ArrayList<String> linha){
        try{
            Formatter gravar = new Formatter(arquivo);
            
            for (int i = 0; i < linha.size(); i++) {
                gravar.format("%s", linha.get(i).toString());
            }
            
            gravar.flush();
            gravar.close();
            return "Salvo com sucesso.";
            
        } catch (FileNotFoundException ex){
            return ex.getMessage();
        }
        
    }
}
