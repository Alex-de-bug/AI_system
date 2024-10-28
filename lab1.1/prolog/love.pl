loves(mas, via).
loves(ads, via).
loves(yay, piz).
loves(piz, yay).

jealous(X, Y) :- loves(X, Z), loves(Y, Z), X\=Y. 
