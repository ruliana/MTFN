package mtfn;

public class MetaphonePtBrFrouxo extends Metaphone {

    public MetaphonePtBrFrouxo(String string) {
        super(string);
    }
    
    protected void algorithm() {
        
        // Não é fonética, mas é o erro mais comum de digitação
        // "cao" ao final a palavra é "ção"
        translate(".(cao)\\s", "S");
        
        // Remover consoantes nos finais das palavras
        // Carlos, Carlo, Marcos, Marco
        translate(NON_VOWEL + "\\s", ""); 
        
        translate("lh", "L");
        
        // Remover Ms, Ns e Ls fracos
        translate("([mnl])" + NON_VOWEL, "");
        
        translate("l", "L");

        translate("ph", "F");
        translate("th", "T");

        translate("g[eiy]", "J");
        translate("g[ao]", "G");
        translate("gu[ei]", "G");
        translate("g", "G");
        
        translate("ch", "S");
        translate("ck", "K");
        translate("c[eiy]", "S");
        translate("c[aou]", "K");
        translate("c", "K");
        translate("ç", "S");
        
        translate("sh", "S"); // Koshi

        translate("\\s(r)", "R");
        // Remover Rs e Ss fracos
        translate("([rs])" + NON_VOWEL, "");
        translate(NON_VOWEL + "(r)", "");
        translate("r", "R");

        translate("(z)\\s", "S");
        translate("z", "S");

        translate("nh", "N");
        translate("n", "N");

       
        // X é transformado em S
        translate("x", "S"); // o resto
        translate("s", "S");

        translate("q", "K");
        translate("w" + VOWEL, "V");
        
        keep("b", "t", "p", "d", "f", "j", "k", "m", "v");
        // Se começar com A ou HA, mantenha A
        translate("\\s(h?a)", "A");
        // Misturamos muito o E e o I. Considere tudo a mesma coisa
        translate("\\s(h?[ei])", "I");
        // Idem com O e U.
        translate("\\s(h?[ou])", "U");
        
        ignore("l" + NON_VOWEL);
        ignore("h");
        ignore(VOWEL);
    }


    protected void prepare() {
        // Essa letras duplicadas são apenas dor de cabeça. Antes de começar, tiramos o excesso.
        // Otto, Rizzo, Millene, Riccardo (é... com dois "c", achei um desses, é mole?) 
        removeMultiples(" ", "c", "g", "l", "t", "p", "d", "f", "j", "k", "m", "v", "n", "z", "s", "r", "b");
    }
}
