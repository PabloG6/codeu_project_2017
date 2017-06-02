private StringBuilder token;
 private String source;
 private int at;
 
 //helper functions
 private int remaining() {
   return source.length() - at;
 }
 
 private char peek() throws IOException {
   if (at < source.length()) {
     return source.charAt(at);
   } else {
     throw new IOException("ERROR: private char peek()");
   }
 }
 
 private char read() throws IOException {
   final char c = peek();
   at += 1;
   return c;
 }
 
 public String next() throws IOException {
   // Skip all leading whitespace
   while (remaining() > 0 && Character.isWhitespace(peek()) {
     read();  // ignore the result because we already know that it is a whitespace character
   }
   if (remaining() <= 0) {
     return null;
   } else if (peek() == ‘”’) {
     readWithQuotes();
   } else {
     readWithNoQuotes();
   }
 }
 
 private String readWithNoQuotes() throws IOException {
   token.setLength(0);  // clear the token
   while (remaining() > 0 && !Character.isWhitespace(peek())) {
     token.append(read());
   }
   return token.toString();
 }
 
 private String readWithQuotes() throws IOException {
   token.setLength(0);  // clear the token
   if (read() != ‘”’) {
     throw new IOException(“Strings must start with opening quote”);
   }
   while (peek() != ‘”’) {
     token.append(read());
   }
   read(); // read the closing the quote that allowed us to exit the loop
   return token.toString();
 }
 
 //main class
 public final class Tokenizer {
   public Tokenizer(String source) { 
     Tokenizer str = new StringTokenizer("this is a test");
     while (str.remaining() > 0) {
         System.out.println(str.nextToken());
     }
   }
   public String next() throws IOException { 
     while (remaining() > 0 && Character.isWhitespace(peek()) {
     read();  // ignore the result because we already know that it is a whitespace character
     }
     if (remaining() <= 0) {
        return null;
     } else if (peek() == ‘”’) {
        readWithQuotes();
     } else {
        readWithNoQuotes();
  }

 }
 }
