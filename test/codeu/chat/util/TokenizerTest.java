//tests Tokenizer in the quote function and the no-quote function

package codeu.chat.util;

import static org.junit.Assert.*; 
import java.io.IOException;
import org.junit.Test;

public final class TokenizerTest {

  //test with quotes
  @Test
  public void testWithQuotes() throws IOException
  {
    final Tokenizer tokenizer = new Tokenizer("\"hello world\" \"how are you\"");
    assertEquals(tokenizer.next(), "hello world");
    assertEquals(tokenizer.next(), "how are you");
    assertEquals(tokenizer.next(), null);
   }
    
   //test with no quotes
   @Test
   public void testWithNoQuotes() throws IOException
   {
     final Tokenizer tokenizer = new Tokenizer("hello world how are you");
     assertEquals(tokenizer.next(), "hello");
     assertEquals(tokenizer.next(), "world");
     assertEquals(tokenizer.next(), "how");
     assertEquals(tokenizer.next(), "are");
     assertEquals(tokenizer.next(), "you");
     assertEquals(tokenizer.next(), null);
    }
}
