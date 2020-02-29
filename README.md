# Lexical_Analyzer
Pass the input and output file path in the parameter 
Run LexAnalyzer


Consider the following EBNF defining 18 token categories ⟨id⟩ through ⟨RBrace⟩:

⟨letter⟩ → a | b | ... | z | A | B | ... | Z
⟨digit⟩ → 0 | 1 | ... | 9
⟨id⟩ → ⟨letter⟩ {⟨letter⟩ | ⟨digit⟩}
⟨int⟩ → [+|−] {⟨digit⟩}+
⟨float⟩ → [+|−] ( {⟨digit⟩}+ "." {⟨digit⟩}  |  "." {⟨digit⟩}+ )
⟨floatE⟩ → (⟨int⟩ | ⟨float⟩) (e|E) [+|−] {⟨digit⟩}+
⟨floatF⟩ → (⟨int⟩ | ⟨float⟩ | ⟨floatE⟩) ("f" | "F")
⟨add⟩ → +
⟨sub⟩ → −
⟨mul⟩ → *
⟨div⟩ → /
⟨lt⟩ → <
⟨le⟩ → "<="
⟨gt⟩ → >
⟨ge⟩ → ">="
⟨eq⟩ → =
⟨LParen⟩ → (
⟨RParen⟩ → )
⟨LBrace⟩ → {
⟨RBrace⟩ → }

⟨letter⟩ and ⟨digit⟩ are not token categories by themselves; 
rather, they are auxiliary categories to assist the definitions of the tokens ⟨id⟩, ⟨int⟩, ⟨float⟩, ⟨floatE⟩, ⟨floatF⟩.

According to the above definitions, the integers and floating-point numbers may be signed with "+" or "−". 
Moreover, the integer or fractional part, but not both, of a string in ⟨float⟩ may be empty. 
The following is a DFA to accept the 18 token categories.

The objective of this project is to implement a lexical analyzer that accepts the 18 token 
categories plus the following keywords, all in lowercase letters only:
    if, then, else, or, and, not, pair, first, second, nil
    
These keywords cannot be used as identifiers, but can be parts of identifiers, like "iff" and "delse". 
In this and the next three projects, the identifiers and keywords are case-sensitive. 
The implementation should be based on the above DFA. 
Lexical analyzer separates the driver and the state-transition function so that the driver will remain invariant and only state-transition functions will change from DFA to DFA. 
The enumerated or integer type is suggested for representation of states.
