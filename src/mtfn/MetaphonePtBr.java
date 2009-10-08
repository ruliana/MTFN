/***************************** COPYRIGHT NOTICES ***********************


The original double metaphone code bears this copyright notice:

  Copyright 2000, Maurice Aubrey <maurice@hevanet.com>.
  All rights reserved.

  This code is based heavily on the C++ implementation by
  Lawrence Philips and incorporates several bug fixes courtesy
  of Kevin Atkinson <kevina@users.sourceforge.net>.

  This module is free software; you may redistribute it and/or
  modify it under the same terms as Perl itself.



The original code is authored by Andrew Dunstan <amdunstan@ncshp.org>, and
<andrew@dunslane.net> and is covered this copyright:

  Copyright 2003, North Carolina State Highway Patrol.
  All rights reserved.

  Permission to use, copy, modify, and distribute this software and its
  documentation for any purpose, without fee, and without a written agreement
  is hereby granted, provided that the above copyright notice and this
  paragraph and the following two paragraphs appear in all copies.

  IN NO EVENT SHALL THE NORTH CAROLINA STATE HIGHWAY PATROL BE LIABLE TO ANY
  PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES,
  INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS
  DOCUMENTATION, EVEN IF THE NORTH CAROLINA STATE HIGHWAY PATROL HAS BEEN
  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

  THE NORTH CAROLINA STATE HIGHWAY PATROL SPECIFICALLY DISCLAIMS ANY
  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED
  HEREUNDER IS ON AN "AS IS" BASIS, AND THE NORTH CAROLINA STATE HIGHWAY PATROL
  HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
  MODIFICATIONS.



The metaphone port is authored by Carlos Costa Jordao <carlosjordao@gmail.com>
and is covered under this copyright:

  Copyright 2008, Carlos Costa Jordao <carlosjordao@gmail.com>.
  All rights reserved.

  Redistribution and use in source and binary forms, with or without modification,
  are permitted provided that the following conditions are met:
  
  1. Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
  2. Redistributions in binary form must reproduce the above copyright notice, this
     list of conditions and the following disclaimer in the documentation and/or
     other materials provided with the distribution.
  
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR 
  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


 ***********************************************************************/
/**
 * http://www.codeproject.com/string/dmetaphone1.asp
 * http://www.cuj.com/documents/s=8038/cuj0006philips/
 * http://www2.varzeapaulista.sp.gov.br/metaphone/
 */
package mtfn;

public class MetaphonePtBr extends Metaphone {

    public MetaphonePtBr(String string) {
        super(string);
    }

    protected void algorithm() {

        // Nomes antigos
        translate("ph", "F"); // Alphonso
        translate("th", "T"); // Martha
        
        translate("lh", "1");
        // Galdêncio ou Gaudêncio? Descartamos como se fosse vogal
        ignore("(l)" + NON_VOWEL);
        translate("l", "L");

        translate("g[eiy]", "J");
        translate("g[ao]", "G");
        translate("gu[ei]", "G");
        translate("g", "G");

        /* C parecia mais simples no começo */

        // O erro de digitação mais comum q existe "cao" = "ção",
        // não faz parte do metaphone, mas...
        translate("cao", "S");
        // "Belchior" (Belkior, não Belxior) parece ser exceção
        // mas me lembro de "Melchior" em algum lugar, também.
        translate("l(chior)\\s", "K2");
        translate("ch", "X");
        translate("ck", "K"); // Volta e meia tem uma "Jackeline"
        translate("cq", "K"); // E um ou outro "Jacques" perdido
        translate("c[eiy]", "S");
        translate("c[aou]", "K");
        translate("c", "K");
        translate("ç", "S");

        translate("rr", "2");
        translate("\\s(r)", "2"); // Raul, Régis
        translate("(r)\\s", "2"); // Adamastor, Maber (ou seria melhor "R"?)
        translate("r", "R"); // Maria, Marcelo (ou seria melhor "2"?)

        translate("(z)\\s", "S"); // Luiz, Tomaz
        translate("z", "Z");

        translate("(n)\\s", "M"); // Renan
        translate("nh", "3");
        translate("n", "N");

        translate("ss", "S");
        translate("\\s(s)", "S"); // sela, Sebastião
        translate("(s)\\s", "S"); // mas, Marcos
        translate("sh", "X"); // Koshi
        translate(VOWEL + "(s)" + VOWEL, "Z"); // asa, Isabel
        translate("sc[ei]", "S"); // asceta
        translate("sc[aou]", "SC"); // masca
        translate("s", "S");

        /* X é um pesadelo... por sorte não temos muitos nomes próprios com X */

        // Algumas sílabas e encontros como "ca", "ai" ou "ei" antes do X
        // parecem provocar o som de CH ao invés de KS
        translate("[ckglrxaeiou][aeiou](x)", "X"); // abacaxi, Aleixo, Alexandre
        translate("\\s(x)", "X"); // Xavier
        translate("x\\s", "KS"); // Félix
        translate("xc[ei]", "S"); // exceção, excelência, excitação
        translate("\\se(x)" + VOWEL, "Z"); // exemplo, exercício, exímio
        translate("\\se(x)" + NON_VOWEL, "S"); // êxtase, exposição
        // mexe, mexido... Uóxinton :D ... mas... e máximo e táxi? :(
        translate("x[ei]", "X");
        translate("x[aou]", "KS"); // sexo, anexo,
        translate("x", "KS"); // o resto

        // O resto
        translate("q", "K");
        translate("\\sh(" + VOWEL + ")", THE_MATCH);
        translate("w" + VOWEL, "V");
        ignore("h");

        // Essas consoantes são fortes, tem o som que tem.
        keep("b", "t", "p", "d", "f", "j", "k", "m", "v");
        
        // Vogal no começo é a própria vogal
        translate("\\s(" + VOWEL + ")", THE_MATCH);
        
        // O resto das vogais são ignoradas
        ignore(VOWEL);
    }

    protected void prepare() {
        // Essa letras duplicadas são apenas dor de cabeça. Antes de começar, tiramos o excesso.
        // Otto, Rizzo, Millene, Riccardo (é... com dois "c", achei um desses, é mole?) 
        removeMultiples(" ", "b", "c", "g", "l", "t", "p", "d", "f", "j", "k", "m", "v", "n", "z");
    }
}
