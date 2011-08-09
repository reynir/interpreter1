.. -*- mode: rst -*-
.. vim: tw=72 sw=2 sts=2 et

=======================
SICP scheme interpreter
=======================




Parsing
=======

Tokenizer.java is the mess that does all parsing. As of commit
e8c529f162e17b305476aaa1f17b6d323c90a2fb it does / can

- read 32-bit integers
- read strings with (at least most of) the usual C escape characters.
- read lists
- read quotes (well, sort of)
- ...

The parser needs fixing. Quotes and special forms have to work. I have
to do some thinking.

Eval
====

Eval resides in Evaluator.java, and currently it can

- evaluate self-evaluating expressions (ints, strings)
- evaluate quotes (quotes are kind of broken though)
- return garbage otherwise
