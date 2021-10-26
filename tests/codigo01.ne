var Int n;
n = 100;
var Int soma;
soma = 0;
var Int i;
i = 0;
var Boolean verd;
verd = true;
var Int kk;
kk = 10000;
var Int kkWhile;
var Int m;
while i <= n && !!verd {
  kkWhile = i%2;
  m = (0+0*0/1);
  if kkWhile == m {
	soma = (--soma) + i*i;
  }
  i = i + 1;
}
var Int somaFor;
somaFor = 0;
var Int quad;
for k in 0..100 {
  quad = k*k;
  if k%2 == 0 {
	   somaFor = somaFor + quad;
  }
}
println "soma = " ++ soma;
println "somaFor = " ++ somaFor;
var Int num;
var String str;
var Boolean souVerd;
if soma == somaFor {
    num = 0;
    if num > 0 {
        str = "sou String";
        for cont in 1..5 {
            souVerd = true;
            println (souVerd ++ " ") ++ cont;
        }
        str = "continuo String";
		println "str = " ++ str;
    }
    else {
        num = 1;
        println "num = " ++ num;
    }
	println "true = " ++ (true >= false);
}
else {
	println "Alguma coisa errada! Soma != SomaFor";
}
if 0 < 1 &&
   (
     (true >= false && "abc" < "cba") 
     && "A" == "A") {
  println "Ufa, deu certo!";
}
