package codeu.chat.util;

import java.util.LinkedList;

public final class Tokenizer {
  private LinkedList<String> tokens = new LinkedList<String>();

  public Tokenizer(String line) {
    StringBuilder token = new StringBuilder();
    boolean inQuotes = false;
    boolean lookingForEscapable = false;

    //reads line and checks for quotes or double slashes, which are ignored to allow tokenization
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if (lookingForEscapable) {
        if (c == '\\' || c == '"') {
          token.append(c);
          lookingForEscapable = false;
        }
        else {
          throw new IllegalArgumentException
            ("Backslash found before unescapable character."+
             "\' can only be found before backslash and quote");
        }
      }
             
      //reacds line and if there is a character following quotes, an IE error is thrown indicating that
      //requested tokenization was not understood
      else {
        if (c == '\\')
          lookingForEscapable = true;
        else if (c == '"') {
          if (inQuotes) {
            if ((i + 1) < line.length() && !Character.isWhitespace(line.charAt(i+1)))
              throw new IllegalArgumentException
              ("Character found right after quotation marks. Individual input sections unclear.");
            tokens.add(token.toString());
            token.setLength(0);
            inQuotes = false;
          }
          else {
            if ((i - 1) > 0 && !Character.isWhitespace(line.charAt(i-1)))
              throw new IllegalArgumentException
              ("Character found right before quotation marks. Individual input sections unclear");
            inQuotes = true;
          }
        }
        else if (Character.isWhitespace(c) && !inQuotes) {
          if (token.length() != 0) {
            tokens.add(token.toString());
            token.setLength(0);
          }
        }
        else {
          token.append(c);
          lookingForEscapable = false;
        }
      }
    }

    tokens.add(token.toString());
    token = null;
  }

  //makes sure entire line is read
  public boolean hasNext() {
    return !tokens.isEmpty();
  }

  public String next() {
    return tokens.removeFirst();
  }

}
