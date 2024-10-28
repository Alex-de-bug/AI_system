killer(butch).
married(mia, marsellus).
dead(zed).
footmassage(mia, den).
goodDancer(alex).
nutritious(alex).
tasty(alex).
tasty(maria).

kills(marcesl, X) :- footmassage(mia, X).
eats(jacob, Y) :- nutritious(Y)!.
eats(jacob, Y) :-  tasty(Y).
