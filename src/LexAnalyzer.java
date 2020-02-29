import java.util.Hashtable;

public abstract class LexAnalyzer extends IO {
    public enum State {
        // non-final states     ordinal number

        Start,              // 0
        Period,             // 1
        E,                  // 2
        EPlusMinus,         // 3

        // final states

        Id,                 // 4
        Int,                // 5
        Float,              // 6
        FloatE,             // 7
        Add,                // 8 +
        Sub,                // 9 -
        Mul,                // 10 *
        Div,                // 11 /
        LParen,             // 12 (
        RParen,             // 13 )

        //Additional States
        LBrace,             // 14 {
        RBrace,             // 15 }
        Lt,                 // 16 <
        Gt,                 // 17 >
        Le,                 // <=
        Ge,                 // >=
        Eq,                 // Eq

        // KeyWords
        Keyword_if,
        Keyword_then,
        Keyword_else,
        Keyword_or,
        Keyword_and,
        Keyword_not,
        Keyword_pair,
        Keyword_first,
        Keyword_second,
        Keyword_nil,

        UNDEF;

        private boolean isFinal() {
            return (this.compareTo(State.Id) >= 0);
        }
    }

    // By enumerating the non-final states first and then the final states,
    // test for a final state can be done by testing if the state's ordinal number
    // is greater than or equal to that of Id.

    // The following variables of "IO" class are used:

    //   static int a; the current input character
    //   static char c; used to convert the variable "a" to the char type whenever necessary

    public static String t; // holds an extracted token
    public static State state; // the current state of the FA

    private static int driver()

    // This is the driver of the FA.
    // If a valid token is found, assigns it to "t" and returns 1.
    // If an invalid token is found, assigns it to "t" and returns 0.
    // If end-of-stream is reached without finding any non-whitespace character, returns -1.

    {
        State nextSt; // the next state of the FA

        t = "";
        state = State.Start;

        if (Character.isWhitespace((char) a))
            a = getChar(); // get the next non-whitespace character
        if (a == -1) // end-of-stream is reached
            return -1;

        while (a != -1) // do the body if "a" is not end-of-stream
        {
            c = (char) a;
            nextSt = nextState(state, c);
            if (nextSt == State.UNDEF) // The FA will halt.
            {
                if (state.isFinal())
                    return 1; // valid token extracted
                else // "c" is an unexpected character
                {
                    t = t + c;
                    a = getNextChar();
                    return 0; // invalid token found
                }
            } else // The FA will go on.
            {
                state = nextSt;
                t = t + c;
                a = getNextChar();
            }
        }

        // end-of-stream is reached while a token is being extracted

        if (state.isFinal())
            return 1; // valid token extracted
        else
            return 0; // invalid token found
    } // end driver

    public static void getToken()

    // Extract the next token using the driver of the FA.
    // If an invalid token is found, issue an error message.

    {
        int i = driver();
        if (i == 0)
            displayln(t + " : Lexical Error, invalid token");
    }

    private static State nextState(State s, char c)

    // Returns the next state of the FA given the current state and input char;
    // if the next state is undefined, UNDEF is returned.

    {
        switch (state) {
            case Start:
                if (Character.isLetter(c)){
                    return State.Id;
                }
                else if (Character.isDigit(c))
                    return State.Int;
                else if (c == '+')
                    return State.Add;
                else if (c == '-')
                    return State.Sub;
                else if (c == '*')
                    return State.Mul;
                else if (c == '/')
                    return State.Div;
                else if (c == '(')
                    return State.LParen;
                else if (c == ')')
                    return State.RParen;

                    // Additional state
                else if (c == '{')
                    return State.LBrace;
                else if (c == '}')
                    return State.RBrace;
                else if (c == '<')
                    return State.Lt;
                else if (c == '>')
                    return State.Gt;
                else if (c == '=')
                    return State.Eq;
                else if (c == '.')
                    return State.Period;
                else
                    return State.UNDEF;
            case Int:
                if (Character.isDigit(c))
                    return State.Int;
                else if (c == '.')
                    return State.Float;
                else if (c == 'e' || c == 'E')
                    return State.E;
                else if (c == 'f' || c == 'F')
                    return State.FloatE;
                else
                    return State.UNDEF;
            case Period:
                if (Character.isDigit(c))
                    return State.Float;
                else
                    return State.UNDEF;
            case Float:
                if (Character.isDigit(c))
                    return State.Float;
                else if (c == 'f' || c == 'F')
                    return State.FloatE;
                else if (c == 'e' || c == 'E')
                    return State.E;
                else
                    return State.UNDEF;
            case E:
                if (Character.isDigit(c))
                    return State.FloatE;
                else if (c == '+' || c == '-')
                    return State.EPlusMinus;
                else
                    return State.UNDEF;
            case EPlusMinus:
                if (Character.isDigit(c))
                    return State.FloatE;
                else
                    return State.UNDEF;
            case FloatE:
                if (Character.isDigit(c))
                    return State.FloatE;
                else if (c == 'f' || c == 'F')
                    return State.FloatE;
                else
                    return State.UNDEF;

                // Additional Cases
            case Add:
                if (Character.isDigit(c))
                    return State.Int;
                else if (c == '.')
                    return State.Period;
                else
                    return State.UNDEF;
            case Sub:
                if (Character.isDigit(c))
                    return State.Int;
                else if (c == '.')
                    return State.Period;
                else
                    return State.UNDEF;
            case Lt:
                if (c == '=')
                    return State.Le;
                else
                    return State.UNDEF;
            case Gt:
                if (c == '=')
                    return State.Ge;
                else
                    return State.UNDEF;

                //KeyWords
            case Id:
                if (Character.isLetterOrDigit(c))
                    return State.Id;
                else
                    return State.UNDEF;
            default:
                return State.UNDEF;
        }
    } // end nextState


    public static void main(String argv[])

    {
        // argv[0]: input file containing source code using tokens defined above
        // argv[1]: output file displaying a list of the tokens

        Hashtable<String, State> key = new Hashtable<>();
        key.put("if", State.Keyword_if);
        key.put("then", State.Keyword_then);
        key.put("else", State.Keyword_else);
        key.put("or", State.Keyword_or);
        key.put("and", State.Keyword_and);
        key.put("not", State.Keyword_not);
        key.put("pair", State.Keyword_pair);
        key.put("first", State.Keyword_first);
        key.put("second", State.Keyword_second);
        key.put("nil", State.Keyword_nil);

        //System.out.println(argv[0]);
        //System.out.println(argv[1]);

        setIO( argv[0], argv[1] );
        //setIO( "input_5", "output_5" );
        //setIO( "C:\\Users\\tah94\\Desktop\\QC_Classes\\Spring_2020\\CS_316\\Projects\\Project_1\\src\\input_7", "C:\\Users\\tah94\\Desktop\\QC_Classes\\Spring_2020\\CS_316\\Projects\\Project_1\\src\\output_7" );


        int i;

        while (a != -1) // while "a" is not end-of-stream
        {
            i = driver(); // extract the next token
            if (i == 1)
                if (key.containsKey(t)) {
                    if (key.get(t) != State.UNDEF) {
                        displayln(t + "  : " + key.get(t).toString());
                    } else
                        displayln(t + " : Lexical Error, invalid token");
                } else
                    displayln(t + "  : " + state.toString());
            else if (i == 0)
                displayln(t + " : Lexical Error, invalid token");
        }

        closeIO();
    }

}