package codeu.chat.util;

import java.io.IOException;

public final class Tokenizer {
  private StringBuilder token;
  private String source;
  private int at;
	
  public Tokenizer(String source) {
	this.token = new StringBuilder();
	this.source = source;
	this.at = 0;
  }
  
  public String next() throws IOException {
	//skip all leading whitespace
	while(remaining() > 0 && Character.isWhitespace(peek())) {
	  read(); //result can be ignored because its definitely a whitespace character
	}
	if(remaining() <= 0) {
      return null;
	} else if(peek() == '"') {
	  //read a token that is surrounded by quotes
      return readWithQuotes();
	} else {
	  //read a token that is not surrounded by quotes
      return readWithNoQuotes();
	}
  }
  
  public int remaining() {
    return source.length() - at;
  }
  
  private char peek() throws IOException {
	if(at < source.length()) {
      return source.charAt(at);
	} else {
	  //throw an exception
		throw new IOException("No char at the position.");
	}
  }
  
  private char read() throws IOException {
	final char c = peek();
	at += 1;
	return c;
  }
  
  private String readWithNoQuotes() throws IOException {
	token.setLength(0);
	while(remaining() > 0 && !Character.isWhitespace(peek())) {
	  token.append(read());
	}
	return token.toString();
  }
  
  private String readWithQuotes() throws IOException {
	token.setLength(0);
	if(read() != '"') {
      throw new IOException("Strings must start with opening quote");
	}
	while(peek() != '=') {
	  token.append(read());
	}
	read();
	return token.toString();
  }

}
